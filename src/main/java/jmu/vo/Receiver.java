package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Receiver {
    private int receiverID;
    private int buyerID;
    private int countyID;
    private String addressDetail;
    private String receiverName;

    //一个收货人--多个订单
    private List<Orders> ordersList;

    //一个收货人--一个买家
    private Buyer buyer;

    //一个收货人--一个区
    private County county;


}
