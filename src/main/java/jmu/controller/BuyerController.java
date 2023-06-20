package jmu.controller;

import jmu.service.*;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/buyerFunction")
public class BuyerController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private OrderItemServcie orderItemServcie;
    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProvinceService provinceService;

    @RequestMapping(value = "/searchByCommodityName", method = RequestMethod.GET)
    public String searchByCommodityName(@RequestParam("commodityName") String commodityName,
                                        Model model){
        List<Commodity> commodityList;
        if(commodityName != null){
            commodityList = commodityService.queryByCommodityName(commodityName);
        }else{
            commodityList = commodityService.queryAll();
        }
        model.addAttribute("commodityList", commodityList);
        return "buyerPage-searchResult";
    }

    @RequestMapping(value = "/commodityDetail", method = RequestMethod.GET)
    public String commodityDetail(@RequestParam("commodityID") int commodityID,
                                        Model model){
        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        model.addAttribute("commodity", commodity);
        return "buyerPage-commodityDetail";
    }

    @RequestMapping(value = "/addCart", method = RequestMethod.GET)
    public String addCart(@RequestParam("commodityID") int commodityID,
                          @RequestParam("orderItemAmount") int orderItemAmount,
                                  Model model){
        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        float money = commodity.getCommodityPrice()*orderItemAmount;
        float FreightInsurance = (float) (money*0.01);
        float allMoney = money+FreightInsurance;
        OrderItem orderItem = new OrderItem();
        orderItem.setSellerID(commodity.getSellerID());
        orderItem.setCommodityID(commodity.getCommodityID());
        orderItem.setOrderItemAmount(orderItemAmount);
        orderItem.setFreightInsurance(FreightInsurance);
        orderItem.setShoppingCart(1);
        orderItem.setAllMoney(allMoney);
        orderItem.setOrderItemState(null);
        boolean flag = orderItemServcie.insert(orderItem);
        if(!flag){//返回插入失败页面
            return "";
        }
        model.addAttribute("orderItem", orderItem);
        return "redirect:/buyerFunction/showCart";
    }

    @RequestMapping(value = "/buyNow", method = RequestMethod.GET)
    public String buyNow(@RequestParam("commodityID") int commodityID,
                         @RequestParam("orderItemAmount") int orderItemAmount,
                          Model model){
        int buyerID = SignAndLoginController.USERSID;

        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        int inventory = commodity.getInventory();
        inventory -= orderItemAmount;
        commodityService.updateInventoryByCommodityID(commodityID,inventory);

        float money = commodity.getCommodityPrice()*orderItemAmount;
        float FreightInsurance = (float) (money*0.01);
        float allMoney = money+FreightInsurance;

        Seller seller = sellerService.queryBySellerID(commodity.getSellerID());
        OrderItem orderItem = new OrderItem();
        orderItem.setSellerID(commodity.getSellerID());
        orderItem.setCommodityID(commodity.getCommodityID());
        orderItem.setOrderItemAmount(orderItemAmount);
        orderItem.setFreightInsurance(FreightInsurance);
        orderItem.setShoppingCart(0);
        orderItem.setAllMoney(allMoney);
        orderItem.setOrderItemState("未发货");
        orderItem.setCommodity(commodity);
        orderItem.setSeller(seller);

        boolean flag = orderItemServcie.insert(orderItem);
        if(!flag){//返回插入失败页面
            return "";
        }

        List<Receiver> receiverList = receiverService.queryByBuyerID(buyerID);
//        List<Receiver> receiverList1 = new ArrayList<>();
//        for(Receiver receiver:receiverList){
//            int countyID = receiver.getCountyID();
//            County county = receiverService.queryCountyByCountyID(countyID);
//            int cityID = county.getCityID();
//            City city = receiverService.queryCityByCityID(cityID);
//            county.setCity(city);
//            int provinceID = city.getProvinceID();
//            Province province = receiverService.queryByProvinceID(provinceID);
//            city.setProvince(province);
//            receiver.setCounty(county);
//            receiverList1.add(receiver);
//        }
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        model.addAttribute("orderItemList", orderItemList);
        float allMoney1 = 0;
        List<Integer> orderItemIDList = new ArrayList<>();
        for(OrderItem orderItem1:orderItemList){
            Integer id = orderItem1.getOrderItemID();
            orderItemIDList.add(id);
            allMoney1 += orderItem1.getAllMoney() + orderItem1.getAllMoney()*0.01;
        }
        IorderItemIDList iorderItemIDList = new IorderItemIDList();
        iorderItemIDList.setOrderItemIDList(orderItemIDList);
        model.addAttribute("orderItemIDList",iorderItemIDList);
        model.addAttribute("allMoney1", allMoney1);
        model.addAttribute("receiverList", receiverList);
        return "buyerPage-paymentConfirmation";
    }

    @RequestMapping(value = "/showInsertReceiver", method = RequestMethod.GET)
    public String showInsertReceiver(Model model){
        List<Province> provinceList = provinceService.queryAllProvince();
        List<City> cityList = provinceService.queryAllCity();
        List<County> countyList = provinceService.queryAllCounty();

        model.addAttribute("provinceList",provinceList);
        model.addAttribute("cityList",cityList);
        model.addAttribute("countyList",countyList);
        return "";
    }

    @RequestMapping(value = "/insertReceiver", method = RequestMethod.GET)
    public String insertReceiver(@RequestParam("countyID") int countyID,
                                  @RequestParam("buyerID") int buyerID,
                                  @RequestParam("addressDetail") String addressDetail,
                                  @RequestParam("receiverName") String receiverName,
                                  Model model){
        int sellerID = SignAndLoginController.USERSID;
        Receiver receiver = new Receiver();
        receiver.setCountyID(countyID);
        receiver.setBuyerID(buyerID);
        receiver.setAddressDetail(addressDetail);
        receiver.setReceiverName(receiverName);

        boolean flag = receiverService.insert(receiver);
        if(!flag){
            return "";
        }
        return "";
    }

    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    public String submitOrder(@RequestParam("orderItemIDList") IorderItemIDList iorderItemIDList,
                              @RequestParam("receiverID") int receiverID,
                              Model model){
        int buyerID = SignAndLoginController.USERSID;

        List<Integer> orderItemIDList = iorderItemIDList.getOrderItemIDList();
        System.out.println("这是orderItemIDList" + orderItemIDList.toString());

        //插入order
        int allMoney = 0;
        Orders order = new Orders();
        order.setReceiverID(receiverID);
        order.setBuyerID(buyerID);
        for(Integer orderItemID : orderItemIDList){
            int IorderItemID = orderItemID.intValue();
            OrderItem orderItem = orderItemServcie.queryByOrderItemID(IorderItemID);
            allMoney += orderItem.getAllMoney();
        }
        order.setFinalMoney(allMoney);

        //这一段是为了获得精确到秒的时间
        Date writeDate = new Date(System.currentTimeMillis());
        // 创建SimpleDateFormat对象，设置日期格式为SQL中的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将Date对象转换为字符串
        String dateString = sdf.format(writeDate);

        order.setOrderTime(dateString);

        int lastInsertID = ordersService.insert(order); //这里记得去调整select语句呜呜呜
        for(Integer orderItemID : orderItemIDList){
            int IorderItemID = orderItemID.intValue();
            orderItemServcie.updateOrderIDByOrderItemID(IorderItemID,lastInsertID); //记得让shoppingCart为0
        }

        return "redirect:/buyerFunction/myOrders";
    }

    //在我的订单找到这个人的所有order,然后显示在前端
    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public String myOrders(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryByBuyerID(buyerID);//orders中包含orderItem
        model.addAttribute("ordersList", ordersList);
        return "buyerPage-myOrderAllOrder";
    }

    @RequestMapping(value="confirmReceipt", method = RequestMethod.GET)
    public String confirmReceipt(@RequestParam("orderItemID") int orderItemID,
                                 Model model){
        int buyerID = SignAndLoginController.USERSID;
        String orderItemState = "已签收";
        boolean flag = orderItemServcie.updateOrderItemStateByOderItemID(orderItemID,orderItemState);
        return "";
    }

    @RequestMapping(value = "/myOrdersUnsend", method = RequestMethod.GET)
    public String myOrdersUnsend(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryByBuyerID(buyerID);//orders中包含orderItem
        List<OrderItem> IorderItemList = new ArrayList<OrderItem>();
        for(Orders orders : ordersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem:orderItemList){
                if(orderItem.getOrderItemState()=="未发货"){
                    IorderItemList.add(orderItem);
                }
            }
        }
        model.addAttribute("orderItemList", IorderItemList);
        return "";
    }

    @RequestMapping(value = "/myOrdersDelivered", method = RequestMethod.GET)
    public String myOrdersDelivered(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryByBuyerID(buyerID);//orders中包含orderItem
        List<OrderItem> IorderItemList = new ArrayList<OrderItem>();
        for(Orders orders : ordersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem:orderItemList){
                if(orderItem.getOrderItemState()=="已送达" || orderItem.getOrderItemState()=="已签收"){
                    IorderItemList.add(orderItem);
                }
            }
        }
        model.addAttribute("IorderItemList", IorderItemList);
        return "";
    }


    @RequestMapping(value = "/showCart", method = RequestMethod.GET)
    public String showCart(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<OrderItem> orderItemList = orderItemServcie.queryByShoppingCart(buyerID);
        List<Receiver> receiverList = receiverService.queryByBuyerID(buyerID);
        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("receiverList", receiverList);
        return "";
    }

    @RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
    public String deleteCart(@RequestParam("orderItemID") int orderItemID,
                          Model model){
        int buyerID = SignAndLoginController.USERSID;
        boolean flag = orderItemServcie.deleteByOrderItemID(orderItemID);
        return "";
    }

    @RequestMapping(value = "/buyFromCart", method = RequestMethod.GET)
    public String buyFromCart(@RequestParam("oderItemIDList") List<Integer> oderItemIDList,
                         Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Receiver> receiverList = receiverService.queryByBuyerID(buyerID);
        List<OrderItem> orderItemList = new ArrayList<>();
        for(Integer orderItemID : oderItemIDList){
            int IorderItemID = orderItemID.intValue();
            OrderItem orderItem = orderItemServcie.queryByOrderItemID(IorderItemID);
            orderItemList.add(orderItem);
        }
        model.addAttribute("orderItemList", orderItemList);
        model.addAttribute("receiverList", receiverList);
        return "";
    }

    @RequestMapping(value = "/myExcel", method = RequestMethod.GET)
    public String myExcel(Model model){
        int buyerID = SignAndLoginController.USERSID;
        int sum = 0;
        float money = 0;
        Buyer buyer = buyerService.queryByBuyerID(buyerID);
        List<Orders> ordersList = ordersService.queryByBuyerID(buyerID);
        for(Orders orders:ordersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem : orderItemList){
                sum += orderItem.getOrderItemAmount();
                money += orderItem.getAllMoney();
            }
        }
        model.addAttribute("buyer", buyer);
        model.addAttribute("sum", sum);
        model.addAttribute("money", money);
        return "buyerPage-excel";
    }

}
