package jmu.mapper;

import jmu.vo.OrderItem;
import jmu.vo.Orders;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderItemMapper {

    @Insert("INSERT INTO orderitem(sellerID, commodityID, orderID, orderItemAmount, freightInsurance," +
            " shoppingCart, allMoney, orderItemState )\n" +
            "VALUES (#{sellerID}, #{commodityID}, #{orderID}, #{orderItemAmount}, #{freightInsurance}, " +
            "#{shoppingCart}, #{allMoney}, #{orderItemState})")
    public boolean insert(OrderItem orderItem);

    //修改订单状态
    @Update("update orderitem\n" +
            "set orderItemState=#{orderItemState}\n" +
            "where orderItemID=#{orderItemID}")
    public boolean updateOrderItemStateByOderItemID(int orderItemID, String orderItemState);

    @Select("select * from orderitem,orders where orderitem.orderID=orders.orderID and shoppingCart=1 and orders.buyerID=#{buyerID}")
    public List<OrderItem> queryByShoppingCart(int buyerID); //查询shoppingChart=1的

    @Delete("DELETE\n" +
            "FROM orderitem\n" +
            "WHERE orderItemID = #{orderItemID}")
    public boolean deleteByOrderItemID(int orderItemID);

    @Select("select * from orderitem where sellerID=#{sellerID}")
    public List<OrderItem> queryBySellerID(int sellerID);

    @Select("select * from orderitem where orderItemID=#{orderItemID}")
    public OrderItem queryByOrderItemID(int orderItemID);

    @Update("update orderitem\n" +
            "set orderID=#{lastInsertID}, shoppingCart=0\n" +
            "where orderItemID=#{orderItemID}")
    public boolean updateOrderIDByOrderItemID(int orderItemID, int lastInsertID);

    @Select("select * from orderitem where orderID=#{orderID}")
    @Results({
            @Result(id = true, property = "orderItemID", column = "orderItemID"),
            @Result(property = "sellerID", column = "sellerID"),
            @Result(property = "commodityID", column = "commodityID"),
            @Result(property = "orderID", column = "orderID"),
            @Result(property = "orderItemAmount", column = "orderItemAmount"),
            @Result(property = "allMoney", column = "allMoney"),
            @Result(property = "orderItemState", column = "orderItemState"),


            @Result(property = "commodity", column = "commodityID",
                    javaType = jmu.vo.Commodity.class,
                    one = @One(select="jmu.mapper.CommodityMapper.queryByCommodityID",
                            fetchType = FetchType.LAZY))
    })
    public List<OrderItem> queryByOrderIDfrom(int orderID);

}
