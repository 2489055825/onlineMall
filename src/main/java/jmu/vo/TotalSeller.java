package jmu.vo;

import lombok.Data;

@Data
public class TotalSeller {
    private int commodityID;//商品ID
    private float totalSales;//总销售金额
    private int totalQuantity;//总销量

}
