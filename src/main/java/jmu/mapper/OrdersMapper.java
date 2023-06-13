package jmu.mapper;

import jmu.vo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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


    //通过商家id找所有订单
    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n")
    public List<Orders> queryBySellerID(int sellerID);

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='未发货'")
    public List<Orders> queryBySellerIDAndStateUnsend(int sellerID); //stateUnSend为未发货

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='已送达'")
    public List<Orders> queryBySellerIDAndStateDelivered(int sellerID); //Delivered 为已送达

    @Select("select * from orders, orderitem\n" +
            "where orderitem.sellerID=#{sellerID}\n" +
            "and orders.orderID = orderitem.orderID\n" +
            "and orderitem.orderitemState='已收货'")
    public List<Orders> queryBySellerIDAndStateGiven(int sellerID); //Given为已收货



}
