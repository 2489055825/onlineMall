package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.UserService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
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
    public User queryBySellerID(int sellerID) {
        User user = userMapper.queryByUserID(sellerID);
        return user;
    }

    @Override
    public boolean updateQuestionByID(int sellerID, String question, String answer) {
        boolean flag = userMapper.updateQuestionByID(sellerID,question,answer);
        return flag;
    }
}
