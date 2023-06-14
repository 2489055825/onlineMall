package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.CommodityService;
import jmu.service.ReceiverService;
import jmu.vo.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {
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
    public List<Commodity> queryByCommodityName(String commodityName) {
        commodityName = "%" + commodityName + "%";
        List<Commodity> commodityList = commodityMapper.queryByCommodityName(commodityName);
        return commodityList;
    }

    @Override
    public List<Commodity> queryAll() {
        List<Commodity> commodityList = commodityMapper.queryAll();
        return commodityList;
    }

    @Override
    public Commodity queryByCommodityID(int commodityID) {
        Commodity commodity = commodityMapper.queryByCommodityID(commodityID);
        return commodity;
    }


}
