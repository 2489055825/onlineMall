package jmu.controller;

import jmu.vo.Commodity;
import jmu.vo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/buyerFunction")
public class BuyerController {
    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value = "/searchByCommodityName", method = RequestMethod.GET)
    public String searchByCommodityName(@RequestParam("commodityName") String commodityName,
                                        Model model){
        List<Commodity> commodityList;
        if(commodityName != null){
            commodityList = commodityService.queryByCommodityName(commodityName);
        }else{
            commodityList = commodityService.queryAll();
        }
        model.addAttribute("commodityList", commodityList)
        return "";
    }

    @RequestMapping(value = "/commodityDetail", method = RequestMethod.GET)
    public String commodityDetail(@RequestParam("commodityID") int commodityID,
                                        Model model){
        Commodity commodity = commodityService.queryByCommodityID(commodityID);
        model.addAttribute("commodity", commodity)
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
        orderItem.setOrderID(0);
        orderItem.setOrderItemAmount(OrderItemAmount);
        orderItem.setFreightInsurance(FreightInsurance);
        orderItem.setShoppingCart(1);
        orderItem.setAllMoney(allMoney);
        orderItem.setOrderItemState(null);

        model.addAttribute("orderItem", orderItem);
        return "";
    }




}
