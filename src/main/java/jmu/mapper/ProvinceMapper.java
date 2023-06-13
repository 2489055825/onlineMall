package jmu.mapper;

import jmu.vo.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProvinceMapper {
    @Select("select * from province where provinceID=#{provinceID}")
    public City queryByProvinceID(int provinceID);


}