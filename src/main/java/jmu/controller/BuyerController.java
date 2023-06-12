package jmu.controller;

import jmu.vo.Commodity;
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


}
