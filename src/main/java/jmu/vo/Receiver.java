package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Receiver {
    private int receiverID;
    private int buyerID;
    private String addressDetail;
    private String receiverName;

    //一个收货人--多个订单
    private List<Orders> orders;

    //一个收货人--一个买家
    private Buyer buyer;

    //一个收货人--一个省
    private Province province;

    //一个收货人--一个市
    private City city;

    //一个收货人--一个区
    private County county;


}
