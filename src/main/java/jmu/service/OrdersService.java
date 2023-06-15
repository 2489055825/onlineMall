package jmu.service;

import jmu.vo.Orders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface OrdersService {
    int insert(Orders order);

    List<Orders> queryByBuyerID(int buyerID);

    List<Orders> queryAllOrdersBySellerID(int sellerID);
}
