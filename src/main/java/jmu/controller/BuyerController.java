package jmu.controller;

import jmu.service.SignAndLoginService;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "";
    }

    @RequestMapping(value = "/commodityDetail", method = RequestMethod.GET)
    public String commodityDetail(@RequestParam("commodityID") int commodityID,
                                        Model model){
        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        model.addAttribute("commodity", commodity);
        return "";
    }

    @RequestMapping(value = "/addCart", method = RequestMethod.GET)
    public String addCart(@RequestParam("commodityID") int commodityID,
                          @RequestParam("OrderItemAmount") int OrderItemAmount,
                                  Model model){
        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        float money = commodity.getCommodityPrice()*OrderItemAmount;
        float FreightInsurance = (float) (money*0.01);
        float allMoney = money+FreightInsurance;
        OrderItem orderItem = new OrderItem();
        orderItem.setSellerID(commodity.getSellerID());
        orderItem.setCommodityID(commodity.getCommodityID());
        orderItem.setOrderItemAmount(OrderItemAmount);
        orderItem.setFreightInsurance(FreightInsurance);
        orderItem.setShoppingCart(1);
        orderItem.setAllMoney(allMoney);
        orderItem.setOrderItemState(null);
        boolean flag = orderItemServcie.insert(orderItem);
        if(!flag){//返回插入失败页面
            return "";
        }
        model.addAttribute("orderItem", orderItem);
        return "";
    }

    @RequestMapping(value = "/buyNow", method = RequestMethod.GET)
    public String buyNow(@RequestParam("commodityID") int commodityID,
                         @RequestParam("OrderItemAmount") int OrderItemAmount,
                          Model model){
        int buyerID = SignAndLoginController.USERSID;

        Commodity commodity = commodityService.queryByCommodityID(commodityID);

        float money = commodity.getCommodityPrice()*OrderItemAmount;
        float FreightInsurance = (float) (money*0.01);
        float allMoney = money+FreightInsurance;
        OrderItem orderItem = new OrderItem();
        orderItem.setSellerID(commodity.getSellerID());
        orderItem.setCommodityID(commodity.getCommodityID());
        orderItem.setOrderItemAmount(OrderItemAmount);
        orderItem.setFreightInsurance(FreightInsurance);
        orderItem.setShoppingCart(0);
        orderItem.setAllMoney(allMoney);
        orderItem.setOrderItemState("未发货");
        boolean flag = orderItemServcie.insert(orderItem);
        if(!flag){//返回插入失败页面
            return "";
        }

        List<Receiver> receiverList = receiverService.queryByBuyerID(buyerID);

        model.addAttribute("orderItem", orderItem);
        model.addAttribute("receiverList", receiverList);
        return "";
    }

    @RequestMapping(value = "/submitOrder", method = RequestMethod.GET)
    public String submitOrder(@RequestParam("orderItemIDList") List<Integer> orderItemIDList,
                              @RequestParam("receiverID") int receiverID,
                              Model model){
        int buyerID = SignAndLoginController.USERSID;

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

        return "";
    }

    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public String myOrders(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryByBuyer(buyerID);//orders中包含orderItem
        model.addAttribute("ordersList", ordersList);
        return "";
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
        List<Orders> ordersList = ordersService.queryByBuyer(buyerID);//orders中包含orderItem
        List<OrderItem> IorderItemList = new ArrayList<OrderItem>();
        for(Orders orders : ordersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem:orderItemList){
                if(orderItem.getOrderItemState()=="未发货"){
                    IorderItemList.add(orderItem);
                }
            }
        }
        model.addAttribute("IorderItemList", IorderItemList);
        return "";
    }

    @RequestMapping(value = "/myOrdersDelivered", method = RequestMethod.GET)
    public String myOrdersDelivered(Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryByBuyer(buyerID);//orders中包含orderItem
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
        model.addAttribute("orderItemList", orderItemList);
        return "";
    }

    @RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
    public String deleteCart(@RequestParam("orderItemID") int orderItemID,
                          Model model){
        int buyerID = SignAndLoginController.USERSID;
        List<OrderItem> orderItemList = orderItemServcie.deleteByOrderItemID(orderItemID);
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
        return "";
    }
}
