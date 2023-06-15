package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.ProvinceService;
import jmu.vo.City;
import jmu.vo.County;
import jmu.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {
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
    public List<Province> queryAllProvince() {
        List<Province> provinceList = provinceMapper.queryAll();
        return provinceList;
    }

    @Override
    public List<City> queryAllCity() {
        List<City> cityList = cityMapper.queryAll();
        return cityList;
    }

    @Override
    public List<County> queryAllCounty() {
        List<County> countyList = countyMapper.queryAll();
        return countyList;
    }
}
