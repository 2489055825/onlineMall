package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Buyer {
    private int buyerID;

    private List<Receiver> receiverList;
    //一个买家--多个订单
    private List<Orders> ordersList;

}
