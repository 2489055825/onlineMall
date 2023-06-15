package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.SellerService;
import jmu.vo.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
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
    public Seller queryBySellerID(int sellerID) {
        Seller seller = sellerMapper.queryBySellerID(sellerID);
        return seller;
    }

    @Override
    public boolean update(Seller seller) {
        boolean flag = sellerMapper.updateBySellerID(seller);
        return flag;
    }
}
