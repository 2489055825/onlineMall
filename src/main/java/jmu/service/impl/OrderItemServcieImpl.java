package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.OrderItemServcie;
import jmu.vo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;

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
}
