package jmu.vo;

//订单商品

import lombok.Data;

@Data
public class OrderItem {
    private int orderItem;
    private int sellerID;
    private int commodityID;
    private int orderID;

    private int orderItemAmount;

    private float freightInsurance;

    private int shoppingCart;
    private float allMoney;


    private String orderItemState;

    //一个订单商品--一个订单
    private Orders orders;

    //一个订单商品--一个商家
    private Sell sell;

    //一个订单商品--一个商品
    private Commodity commodity;



}