package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Seller {
    private int sellerID;
    private String shopName;

    //一个商家--多个订单商品
    private List<OrderItem> orderItemList;
    //一个商家--多个优惠
    private List<Sell> sellList;
    //一个商家--多个商品
    private List<Commodity> commodityList;

}
