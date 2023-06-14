package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.OrderItemServcie;
import jmu.vo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServcieImpl implements OrderItemServcie {
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private CountyMapper countyMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private ReceiverMapper receiverMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private SellMapper sellMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean insert(OrderItem orderItem) {
        boolean flag = orderItemMapper.insert(orderItem);
        return flag;
    }

    @Override
    public OrderItem queryByOrderItemID(int iorderItemID) {
        OrderItem orderItem = orderItemMapper.queryByOrderItemID(iorderItemID);
        return orderItem;
    }

    @Override
    public boolean updateOrderIDByOrderItemID(int iorderItemID, int lastInsertID) {
        boolean flag = orderItemMapper.updateOrderIDByOrderItemID(iorderItemID,lastInsertID);
        return flag;
    }

    @Override
    public boolean deleteByOrderItemID(int orderItemID) {
        boolean flag = orderItemMapper.deleteByOrderItemID(orderItemID);
        return flag;
    }

    @Override
    public boolean updateOrderItemStateByOderItemID(int orderItemID, String orderItemState) {
        boolean flag = orderItemMapper.updateOrderItemStateByOderItemID(orderItemID, orderItemState);
        return flag;
    }

    @Override
    public List<OrderItem> queryByShoppingCart(int buyerID) {
        List<OrderItem> orderItemList = orderItemMapper.queryByShoppingCart(buyerID);
        return null;
    }
}
