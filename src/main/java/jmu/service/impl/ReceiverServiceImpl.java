package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.ReceiverService;
import jmu.vo.Receiver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceiverServiceImpl implements ReceiverService {
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
    public List<Receiver> queryByBuyerID(int buyerID) {
        List<Receiver> receiverList = receiverMapper.queryByBuyerID(buyerID);
        return receiverList;
    }
}
