package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Orders {
    private int orderID;
    private int buyerID;
    private int receiverID;
    private float finalMoney;
    private String orderTime;

    //一个订单--多个订单商品
    private List<OrderItem> orderItemList;

    //一个订单--一个收货人
    private Receiver receiver;

    //一个订单--一个买家
    private Buyer buyer;




}
