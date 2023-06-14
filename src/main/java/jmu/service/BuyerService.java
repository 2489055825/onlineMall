package jmu.service;

import jmu.vo.Buyer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BuyerService {

    Buyer queryByBuyerID(int buyerID);
}
