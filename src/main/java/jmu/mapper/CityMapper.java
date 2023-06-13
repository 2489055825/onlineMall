package jmu.mapper;

import jmu.vo.City;
import jmu.vo.Sell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CityMapper {
    @Select("select * from city where cityID=#{cityID}")
    public City queryByCityID(int cityID);


}
