package jmu.mapper;

import jmu.vo.OrderItem;
import jmu.vo.Orders;
import org.apache.ibatis.annotations.*;
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

    @Select("select * from orderitem where shoppingCart=1")
    public List<OrderItem> queryByShoppingChart(); //查询shoppingChart=1的

    @Delete("DELETE\n" +
            "FROM orderitem\n" +
            "WHERE orderItemID = #{orderItemID}")
    public boolean deleteByOrderItemID(int orderItemID);

    @Select("select * from orderitem where sellerID=#{sellerID}")
    public List<OrderItem> queryBySellerID(int sellerID);

    @Select("select * from orderitem where orderItemID=#{orderItemID}")
    public List<OrderItem> queryByOrderItemID(int orderItemID);




}
