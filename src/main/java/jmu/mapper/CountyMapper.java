package jmu.mapper;

import jmu.vo.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CountyMapper {
    @Select("select * from county where countyID=#{countyID}")
    public City queryByCountyID(int countyID);
}
