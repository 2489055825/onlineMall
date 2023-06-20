package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.BuyerService;
import jmu.vo.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
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
    public Buyer queryByBuyerID(int buyerID) {
        Buyer buyer = buyerMapper.queryByBuyerIDfrom(buyerID);
        return buyer;
    }
}
