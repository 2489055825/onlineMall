package jmu.controller;

import jmu.service.SignAndLoginService;
import jmu.vo.Commodity;
import jmu.vo.OrderItem;
import jmu.vo.Orders;
import jmu.vo.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
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

        boolean flag = ordersService.insert(order);
        if(!flag){//返回插入失败页面
            return "";
        }

        return "";
    }


}
