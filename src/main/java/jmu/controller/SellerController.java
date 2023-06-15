package jmu.controller;

import jmu.service.*;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sellerFunction")
public class SellerController {
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


    //所有订单功能
    @RequestMapping(value = "/allOrders", method = RequestMethod.GET)
    public String allOrders(Model model){
        int sellerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryAllOrdersBySellerID(sellerID); //每一个order都要联系一个List<OrderItem>
        List<OrderItem> orderItemList = new ArrayList<>();
        for(Orders orders:ordersList){
            orderItemList.addAll(orders.getOrderItemList());
        }

        model.addAttribute("orderItemList", orderItemList);
        return "";
    }

    @RequestMapping(value = "/ordersUnsend", method = RequestMethod.GET)
    public String ordersUnsend(Model model){
        int sellerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryAllOrdersBySellerID(sellerID);//每一个order都要联系一个List<OrderItem>
        List<OrderItem> orderItemList = new ArrayList<>();
        for(Orders orders:ordersList){
            List<OrderItem> orderItemList1 = orders.getOrderItemList();
            for(OrderItem orderItem:orderItemList1){
                if("未发货".equals(orderItem.getOrderItemState())){
                    orderItemList.add(orderItem);
                }
            }
        }
        model.addAttribute("orderItemList", orderItemList);
        return "";
    }

    @RequestMapping(value = "/changeOrderItemState", method = RequestMethod.GET)
    public String allOrders(@RequestParam("orderItemID") int orderItemID,
                            Model model){
        int sellerID = SignAndLoginController.USERSID;
        String orderItemState = "已送达";
        orderItemServcie.updateOrderItemStateByOderItemID(orderItemID,orderItemState);
        return "";
    }

    //所有商品功能
    @RequestMapping(value = "/showCommodity", method = RequestMethod.GET)
    public String showCommodity(Model model){
        int sellerID = SignAndLoginController.USERSID;
        List<Commodity> commodityList = commodityService.queryBySellerID(sellerID);
        model.addAttribute("commodityList",commodityList);
        return "";
    }

    @RequestMapping(value = "/updateCommodity", method = RequestMethod.GET)
    public String updateCommodity(@RequestParam("commodityID") int commodityID,
                                  @RequestParam("commodityName") String commodityName,
                                  @RequestParam("commodityPhoto") String commodityPhoto,
                                  @RequestParam("commodityPrice") int commodityPrice,
                                  @RequestParam("inventory") int inventory,
                                  Model model){
        int sellerID = SignAndLoginController.USERSID;
        Commodity commodity = new Commodity();
        commodity.setCommodityID(commodityID);
        commodity.setSellerID(sellerID);
        commodity.setCommodityName(commodityName);
        commodity.setCommodityPhoto(commodityPhoto);
        commodity.setCommodityPrice(commodityPrice);
        commodity.setInventory(inventory);
        boolean flag = commodityService.updateCommodityByCommodityID(commodity);
        if(!flag){
            return "";
        }
        return "";
    }

    @RequestMapping(value = "/deleteCommodity", method = RequestMethod.GET)
    public String deleteCommodity(@RequestParam("commodityID") int commodityID,
                                  Model model){
        int sellerID = SignAndLoginController.USERSID;
        boolean flag = commodityService.deleteCommodityByCommodityID(commodityID);
        if(!flag){
            return "";
        }
        return "";
    }

    @RequestMapping(value = "/insertCommodity", method = RequestMethod.GET)
    public String deleteCommodity(@RequestParam("commodityName") String commodityName,
                                  @RequestParam("commodityPhoto") String commodityPhoto,
                                  @RequestParam("commodityPrice") int commodityPrice,
                                  @RequestParam("inventory") int inventory,
                                  Model model){
        int sellerID = SignAndLoginController.USERSID;
        Commodity commodity = new Commodity();
        commodity.setSellerID(sellerID);
        commodity.setCommodityName(commodityName);
        commodity.setCommodityPhoto(commodityPhoto);
        commodity.setCommodityPrice(commodityPrice);
        commodity.setInventory(inventory);
        boolean flag = commodityService.insertCommodity(commodity);
        if(!flag){
            return "";
        }
        return "";
    }

    //显示商家信息
    @RequestMapping(value = "/showSeller", method = RequestMethod.GET)
    public String showSeller(Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = sellerService.queryBySellerID(sellerID);
        model.addAttribute("seller",seller);
        return "";
    }

    @RequestMapping(value = "/updateSeller", method = RequestMethod.GET)
    public String updateSeller(@RequestParam("shopName") String shopName,
                               Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = new Seller();
        seller.setSellerID(sellerID);
        seller.setShopName(shopName);
        boolean flag = sellerService.update(seller);
        if(!flag){
            return "";
        }
        return "";
    }

    @RequestMapping(value = "/myExcel", method = RequestMethod.GET)
    public String updateSeller(Model model){
        int sellerID = SignAndLoginController.USERSID;
        List<OrderItem> orderItemList = orderItemServcie.queryBySellerID(sellerID);

        Map<Integer, Float> totalSalesMap = new HashMap<>(); // 记录商品ID对应的总销售金额
        Map<Integer, Integer> totalQuantityMap = new HashMap<>(); // 记录商品ID对应的总销量

        for (OrderItem orderItem : orderItemList) {
            int commodityID = orderItem.getCommodityID();
            float allMoney = orderItem.getAllMoney();
            int orderItemAmount = orderItem.getOrderItemAmount();

            // 更新总销售金额
            if (totalSalesMap.containsKey(commodityID)) {
                float currentTotalSales = totalSalesMap.get(commodityID);
                float updatedTotalSales = currentTotalSales + allMoney;
                totalSalesMap.put(commodityID, updatedTotalSales);
            } else {
                totalSalesMap.put(commodityID, allMoney);
            }

            // 更新总销量
            if (totalQuantityMap.containsKey(commodityID)) {
                int currentTotalQuantity = totalQuantityMap.get(commodityID);
                int updatedTotalQuantity = currentTotalQuantity + orderItemAmount;
                totalQuantityMap.put(commodityID, updatedTotalQuantity);
            } else {
                totalQuantityMap.put(commodityID, orderItemAmount);
            }
        }

        List<TotalSeller> totalSellerList = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : totalSalesMap.entrySet()) {
            int commodityID = entry.getKey();
            float totalSales = entry.getValue();
            int totalQuantity = totalQuantityMap.get(commodityID);
            TotalSeller totalSeller = new TotalSeller();
            totalSeller.setCommodityID(commodityID);
            totalSeller.setTotalSales(totalSales);
            totalSeller.setTotalQuantity(totalQuantity);
            totalSellerList.add(totalSeller);
        }

        model.addAttribute("totalSellerList",totalSellerList);

        return "";
    }

}
