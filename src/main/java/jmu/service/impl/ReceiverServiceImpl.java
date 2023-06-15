package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.ReceiverService;
import jmu.vo.City;
import jmu.vo.County;
import jmu.vo.Province;
import jmu.vo.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    @Override
    public County queryCountyByCountyID(int countyID) {
        County county = countyMapper.queryByCountyIDfrom(countyID);
        return county;
    }

    @Override
    public City queryCityByCityID(int cityID) {
        City city = cityMapper.queryByCityIDfrom(cityID);
        return city;
    }

    @Override
    public Province queryByProvinceID(int provinceID) {
        Province province = provinceMapper.queryByProvinceID(provinceID);
        return province;
    }

    @Override
    public boolean insert(Receiver receiver) {
        boolean flag = receiverMapper.insert(receiver);
        return flag;
    }
}
