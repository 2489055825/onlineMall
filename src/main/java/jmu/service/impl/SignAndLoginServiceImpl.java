package jmu.service.impl;

import jmu.service.SignAndLoginService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;

public class SignAndLoginServiceImpl implements SignAndLoginService {

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
