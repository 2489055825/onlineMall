package jmu.service.impl;

import jmu.mapper.*;
import jmu.service.SignAndLoginService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignAndLoginServiceImpl implements SignAndLoginService {

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
    public boolean sign(User user) {
        boolean flag = userMapper.insert(user);
        return flag;
    }

    @Override
    public User login(String account, String password) {
        User user = userMapper.queryByAccount(account);
        if(!password.equals(user.getPassword())){
            user = null;
        }
        return user;
    }

    @Override
    public User findPassword(String account) {
        User user = userMapper.queryByAccount(account);
        return user;
    }


}
