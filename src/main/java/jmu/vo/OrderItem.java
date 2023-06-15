package jmu.vo;

//订单商品

import lombok.Data;

@Data
public class OrderItem {
    private int orderItemID;
    private int sellerID;
    private int commodityID;
    private Integer orderID;
    private int orderItemAmount;
    private float freightInsurance;
    private int shoppingCart;
    private float allMoney;
    private String orderItemState;

    //一个订单商品--一个商家
    private Seller seller;
    //一个订单商品--一个订单
    private Orders orders;

    //一个订单商品--一个商品
    private Commodity commodity;



}
