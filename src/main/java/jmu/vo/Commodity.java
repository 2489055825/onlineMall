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
    private int inventory; //库存

    //一个商品--一个商家
    private Seller seller;

    //一个商品--多个订单商品
    private List<OrderItem> orderItemList;
}
