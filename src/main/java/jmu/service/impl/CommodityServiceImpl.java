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

    @Override
    public boolean updateInventoryByCommodityID(int commodityID, int inventory) {
        boolean flag = commodityMapper.updateInventoryByCommodityID(commodityID, inventory);
        return flag;
    }

    @Override
    public List<Commodity> queryBySellerID(int sellerID) {
        List<Commodity> commodityList = commodityMapper.queryBySellerID(sellerID);
        return commodityList;
    }

    @Override
    public boolean updateCommodityByCommodityID(Commodity commodity) {
        boolean flag = commodityMapper.updateByCommodityID(commodity);
        return flag;
    }

    @Override
    public boolean deleteCommodityByCommodityID(int commodityID) {
        boolean flag = commodityMapper.deleteByCommodityID(commodityID);
        return flag;
    }

    @Override
    public boolean insertCommodity(Commodity commodity) {
        Boolean flag = commodityMapper.insert(commodity);
        return flag;
    }


}
