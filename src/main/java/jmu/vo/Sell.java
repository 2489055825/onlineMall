package jmu.vo;

import lombok.Data;

@Data
public class Sell {
    private int sellID;
    private int sellerID;
    private int sellCondition;
    private int sellMoney;

    //一个优惠--一个商家
    private Seller seller;
}
