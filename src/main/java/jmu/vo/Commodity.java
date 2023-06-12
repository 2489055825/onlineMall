package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Commodity {
    private int commodityID;
    private int orderItemID;
    private int sellerID;
    private String commodityName;
    private String commodityPhoto;
    private float commodityPrice;
    private int inventory;

    //一个商品--多个商家
    private List<Seller> sellers;

    //一个商品--一个订单商品
    private OrderItem orderItem;


}
