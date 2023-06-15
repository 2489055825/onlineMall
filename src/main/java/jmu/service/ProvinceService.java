package jmu.service;

import jmu.vo.City;
import jmu.vo.County;
import jmu.vo.Province;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProvinceService {

    List<Province> queryAllProvince();

    List<City> queryAllCity();

    List<County> queryAllCounty();
}
