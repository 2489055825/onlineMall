package jmu.mapper;

import jmu.vo.City;
import jmu.vo.Sell;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CityMapper {
    @Select("select * from city where cityID=#{cityID}")
    @Results({
            @Result(id = true, property = "cityID", column = "cityID"),
            @Result(property = "provinceID", column = "provinceID"),
            @Result(property = "cityName", column = "cityName"),

            @Result(property = "province", column = "provinceID",
                    javaType = jmu.vo.Province.class,
                    one = @One(select="jmu.mapper.ProvinceMapper.queryByProvinceID",
                            fetchType = FetchType.LAZY))
    })
    public City queryByCityID(int cityID);

    @Select("select * from city where cityID=#{cityID}")
    public City queryByCityIDfrom(int cityID);

}
