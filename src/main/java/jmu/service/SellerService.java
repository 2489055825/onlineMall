package jmu.service;

import jmu.vo.Seller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface SellerService {

    Seller queryBySellerID(int sellerID);
}
