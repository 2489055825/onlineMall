package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.OrdersService;
import jmu.vo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
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
    public int insert(Orders order) {
        boolean flag = ordersMapper.insertOrder(order);
        int lastInsertID = ordersMapper.getLastInsertID();
        return lastInsertID;
    }

    @Override
    public List<Orders> queryByBuyerID(int buyerID) {
        List<Orders> ordersList = ordersMapper.queryByBuyerID(buyerID);
        return ordersList;
    }
}
