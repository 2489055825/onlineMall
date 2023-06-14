package jmu.service;

import jmu.vo.Receiver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ReceiverService {
    List<Receiver> queryByBuyerID(int buyerID);
}
