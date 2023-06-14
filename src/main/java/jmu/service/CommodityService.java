package jmu.service;

import jmu.vo.Commodity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CommodityService {
    List<Commodity> queryByCommodityName(String commodityName);

    List<Commodity> queryAll();

    Commodity queryByCommodityID(int commodityID);
}
