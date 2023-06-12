package jmu.service;


import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface SignAndLoginService {
    boolean sign(User user);
    User login(String account, String password);
    User findPassword(String account);

}
