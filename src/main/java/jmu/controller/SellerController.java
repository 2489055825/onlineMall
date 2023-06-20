package jmu.controller;

import jmu.service.*;
import jmu.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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
    @Autowired
    private UserService userService;


    //所有订单功能
    @RequestMapping(value = "/allOrders", method = RequestMethod.GET)
    public String allOrders(Model model){
        int sellerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryAllOrdersBySellerID(sellerID); //每一个order都要联系一个List<OrderItem>
//        List<OrderItem> orderItemList = new ArrayList<>();
//        for(Orders orders:ordersList){
//            orderItemList.addAll(orders.getOrderItemList());
//        }
        List<Orders> filteredOrdersList = new ArrayList<>();
        Set<Integer> processedOrderIDs = new HashSet<>();

        for (Orders orders : ordersList) {
            int orderID = orders.getOrderID();
            if (!processedOrderIDs.contains(orderID)) {
                filteredOrdersList.add(orders);
                processedOrderIDs.add(orderID);
            }
        }

        List<List<OrderItem>> orderItemListList = new ArrayList<>();
        for(Orders orders:filteredOrdersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(OrderItem orderItem:orderItemList){
                if(orderItem.getSellerID() != sellerID){
                    orderItemList.remove(orderItem);
                }
            }
            orderItemListList.add(orderItemList);
        }

        for(int i = 0; i < filteredOrdersList.size(); i++){
            filteredOrdersList.get(i).setOrderItemList(orderItemListList.get(i));
        }

        model.addAttribute("ordersList", filteredOrdersList);
        model.addAttribute("orderItemListList", orderItemListList);
        return "sellerPage-allOrder";
    }

    @RequestMapping(value = "/ordersUnsend", method = RequestMethod.GET)
    public String ordersUnsend(Model model){

//        int sellerID = SignAndLoginController.USERSID;
//        List<Orders> ordersList = ordersService.queryAllOrdersBySellerID(sellerID);//每一个order都要联系一个List<OrderItem>
//        List<OrderItem> orderItemList = new ArrayList<>();
//        for(Orders orders:ordersList){
//            List<OrderItem> orderItemList1 = orders.getOrderItemList();
//            for(OrderItem orderItem:orderItemList1){
//                if("未发货".equals(orderItem.getOrderItemState())){
//                    orderItemList.add(orderItem);
//                }
//            }
//        }
//        model.addAttribute("orderItemList", orderItemList);
        int sellerID = SignAndLoginController.USERSID;
        List<Orders> ordersList = ordersService.queryAllOrdersBySellerID(sellerID); //每一个order都要联系一个List<OrderItem>
//        List<OrderItem> orderItemList = new ArrayList<>();
//        for(Orders orders:ordersList){
//            orderItemList.addAll(orders.getOrderItemList());
//        }
        List<Orders> filteredOrdersList = new ArrayList<>();
        Set<Integer> processedOrderIDs = new HashSet<>();

        for (Orders orders : ordersList) {
            int orderID = orders.getOrderID();
            if (!processedOrderIDs.contains(orderID)) {
                filteredOrdersList.add(orders);
                processedOrderIDs.add(orderID);
            }
        }

        List<List<OrderItem>> orderItemListList = new ArrayList<>();
        for(Orders orders:filteredOrdersList){
            List<OrderItem> orderItemList = orders.getOrderItemList();
            for(int i = 0; i < orderItemList.size(); i++){
                OrderItem orderItem = orderItemList.get(i);
                if(orderItem.getSellerID() != sellerID){
                    orderItemList.remove(orderItem);
                    i--;
                }else if(!orderItem.getOrderItemState().equals("未发货")){
                    orderItemList.remove(orderItem);
                    i--;
                }
            }
            orderItemListList.add(orderItemList);
        }

        for(int i = 0; i < filteredOrdersList.size(); i++){
            filteredOrdersList.get(i).setOrderItemList(orderItemListList.get(i));
            if(filteredOrdersList.get(i).getOrderItemList()==null){
                filteredOrdersList.remove(i);
            }
        }

        Seller seller = sellerService.queryBySellerID(sellerID);
        model.addAttribute("seller", seller);
        model.addAttribute("ordersList", filteredOrdersList);

        return "sellerPage-orderUnsend";
    }

    @RequestMapping(value = "/changeOrderItemState", method = RequestMethod.GET)
    public String allOrders(@RequestParam("orderItemID") int orderItemID,
                            Model model){
        int sellerID = SignAndLoginController.USERSID;
        String orderItemState = "已送达";
        orderItemServcie.updateOrderItemStateByOderItemID(orderItemID,orderItemState);
        return "redirect:/sellerFunction/allOrders";
    }

    //所有商品功能
    @RequestMapping(value = "/showCommodity", method = RequestMethod.GET)
    public String showCommodity(Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = sellerService.queryBySellerID(sellerID);
        List<Commodity> commodityList = commodityService.queryBySellerID(sellerID);
        model.addAttribute("commodityList",commodityList);
        model.addAttribute("seller", seller);
        return "sellerPage-allCommodity";
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
        return "redirect:/sellerFunction/showCommodity";
    }

    @RequestMapping(value = "/deleteCommodity", method = RequestMethod.GET)
    public String deleteCommodity(@RequestParam("commodityID") int commodityID,
                                  Model model){
        int sellerID = SignAndLoginController.USERSID;
        boolean flag = commodityService.deleteCommodityByCommodityID(commodityID);
        if(!flag){
            return "";
        }
        return "redirect:/sellerFunction/showCommodity";
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
        return "redirect:/sellerFunction/showCommodity";
    }

    //显示商家信息
    @RequestMapping(value = "/showSeller", method = RequestMethod.GET)
    public String showSeller(Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = sellerService.queryBySellerID(sellerID);
        User user = userService.queryBySellerID(sellerID);
        model.addAttribute("seller",seller);
        model.addAttribute("user",user);
        return "sellerPage-myInformation";
    }

    @RequestMapping(value = "/updateSeller", method = RequestMethod.GET)
    public String updateSeller(@RequestParam("shopName") String shopName,
                               @RequestParam("question") String question,
                               @RequestParam("answer") String answer,
                               Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = new Seller();
        seller.setSellerID(sellerID);
        seller.setShopName(shopName);
        boolean flag1 = userService.updateQuestionByID(sellerID,question,answer);
        boolean flag = sellerService.update(seller);
        if(!flag || !flag1){
            return "";
        }
        return "redirect:/sellerFunction/showSeller";
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
            Commodity commodity = commodityService.queryByCommodityID(commodityID);
            totalSeller.setCommodity(commodity);
            totalSeller.setTotalSales(totalSales);
            totalSeller.setTotalQuantity(totalQuantity);
            totalSellerList.add(totalSeller);
        }

        Seller seller = sellerService.queryBySellerID(sellerID);
        model.addAttribute("seller",seller);
        model.addAttribute("totalSellerList",totalSellerList);

        return "sellerPage-excel";
    }

    //其实是显示更新商品
    @RequestMapping(value = "/showUpdateSeller", method = RequestMethod.GET)
    public String showUpdateSeller(@RequestParam("commodityID") int commodityID,
                               Model model){
        int sellerID = SignAndLoginController.USERSID;
        model.addAttribute("commodityID",commodityID);
        return "sellerPage-updateCommodity";
    }

    @RequestMapping(value = "/showUpdateInformation", method = RequestMethod.GET)
    public String showUpdateInformation(Model model){
        int sellerID = SignAndLoginController.USERSID;
        Seller seller = sellerService.queryBySellerID(sellerID);
        User user = userService.queryBySellerID(sellerID);
        model.addAttribute("seller",seller);
        model.addAttribute("user",user);
        return "sellerPage-updateInformation";
    }

}
