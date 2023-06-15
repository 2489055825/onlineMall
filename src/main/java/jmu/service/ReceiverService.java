package jmu.service;

import jmu.vo.City;
import jmu.vo.County;
import jmu.vo.Province;
import jmu.vo.Receiver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ReceiverService {
    List<Receiver> queryByBuyerID(int buyerID);

    County queryCountyByCountyID(int countyID);

    City queryCityByCityID(int cityID);

    Province queryByProvinceID(int provinceID);
}
