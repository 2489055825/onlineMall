package jmu.service;

import jmu.vo.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface OrderItemServcie {
    boolean insert(OrderItem orderItem);
}
