package jmu.service;

import jmu.vo.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface OrderItemServcie {
    boolean insert(OrderItem orderItem);

    OrderItem queryByOrderItemID(int iorderItemID);

    boolean updateOrderIDByOrderItemID(int iorderItemID, int lastInsertID);


    boolean deleteByOrderItemID(int orderItemID);

    boolean updateOrderItemStateByOderItemID(int orderItemID, String orderItemState);

    List<OrderItem> queryByShoppingCart(int buyerID);

    List<OrderItem> queryBySellerID(int sellerID);
}
