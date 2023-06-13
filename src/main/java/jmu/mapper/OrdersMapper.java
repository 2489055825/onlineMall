package jmu.mapper;

import jmu.vo.Orders;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersMapper {

    @Insert("INSERT INTO orders(receiverID, buyerID, finalMoney, orderTime)\n" +
            "VALUES (#{receiverID}, #{buyerID}, #{finalMoney}, #{orderTime})")
    public boolean insertOrder(Orders orders);

    @Select("select * from orders where buyerID=#{buyerID}")
    public List<Orders> queryByBuyerID(int buyerID);


    //通过商家id找所有订单
    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n")
//    @Results({
//            @Result(id = true, property = "orderID", column = "orderID"),
//            @Result(property = "buyerID", column = "buyerID"),
//            @Result(property = "receiverID", column = "receiverID"),
//            @Result(property = "finalMoney", column = "addressDetail"),
//            @Result(property = "orderTime", column = "orderTime"),
//
//            @Result(property = "orderItemList", column = "receiverID",
//                    javaType = List.class,
//                    many = @Many(select="jmu.mapper.OrdersMapper.queryByReceiverIDfrom",
//                            fetchType = FetchType.LAZY)),
//            @Result(property = "buyer", column = "buyerID",
//                    javaType = jmu.vo.Buyer.class,
//                    one = @One(select="jmu.mapper.BuyerMapper.queryByBuyerIDfrom",
//                            fetchType = FetchType.LAZY)),
//            @Result(property = "county", column = "countyID",
//                    javaType = jmu.vo.County.class,
//                    one = @One(select="jmu.mapper.CountyMapper.queryByCountyIDfrom",
//                            fetchType = FetchType.LAZY))
//    })
    public List<Orders> queryBySellerID(int sellerID);

    @Select("select * from orders, orderitem\n" +
            "where orders.buyerID=#{buyerID}\n" +
            "and orders.orderID = orderitem.orderID\n"+
            "and orderitem.orderitemState='未发货'\n")
    public List<Orders> queryByBuyerIDAndStateUnsend(int buyerID); //stateUnsend为未发货

    @Select("select * from orders, orderitem\n" +
            "where orders.buyerID=#{buyerID}\n" +
            "and orders.orderID = orderitem.orderID\n"+
            "and orderitem.orderitemState='已送达'\n")
    public List<Orders> queryByBuyerIDAndStateDelivered(int buyerID); //Delivered 为已送达

    @Select("select * from orders, orderitem\n" +
            "where orders.buyerID=#{buyerID}\n" +
            "and orders.orderID = orderitem.orderID\n"+
            "and orderitem.orderitemState='已发货'\n")
    public List<Orders> queryByBuyerIDAndStateGiven(int buyerID); //Given为已收货

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='未发货'")
    public List<Orders> queryBySellerIDAndStateUnsend(int sellerID); //stateUnSend为未发货

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='已送达'")
    public List<Orders> queryBySellerIDAndStateDelivered(int sellerID); //Delivered为已送达

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='已收货'")
    public List<Orders> queryBySellerIDAndStateGiven(int sellerID); //Given为已收货

    //通过商家id找所有订单
    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n")
    public List<Orders> queryBySellerIDfrom(int sellerID);

    @Select("select * from orders\n" +
            "where receiverID=#{receiverID}\n")
    public List<Orders> queryByReceiverIDfrom(int receiverID);


}
