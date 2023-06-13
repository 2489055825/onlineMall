package jmu.mapper;

import jmu.vo.City;
import jmu.vo.County;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CountyMapper {
    @Select("select * from county where countyID=#{countyID}")
    @Results({
            @Result(id = true, property = "countyID", column = "countyID"),
            @Result(property = "countyName", column = "countyName"),
            @Result(property = "cityID", column = "cityID"),

            @Result(property = "city", column = "cityID",
                    javaType = jmu.vo.City.class,
                    one = @One(select="jmu.mapper.CityMapper.queryByCityIDfrom",
                            fetchType = FetchType.LAZY))
    })
    public City queryByCountyID(int countyID);

    @Select("select * from county where countyID=#{countyID}")
    public County queryByCountyIDfrom(int countyID);
}
