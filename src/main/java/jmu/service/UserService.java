package jmu.service;

import jmu.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface UserService {

    User queryBySellerID(int sellerID);
}
