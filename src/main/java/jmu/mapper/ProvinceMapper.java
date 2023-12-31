package jmu.mapper;

import jmu.vo.City;
import jmu.vo.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProvinceMapper {
    @Select("select * from province where provinceID=#{provinceID}")
    public Province queryByProvinceID(int provinceID);


    @Select("select * from province where provinceID=#{provinceID}")
    public Province queryByProvinceIDfrom(int provinceID);

    @Select("select * from province")
    public List<Province> queryAll();
}
