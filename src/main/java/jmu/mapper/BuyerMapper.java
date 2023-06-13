package jmu.mapper;

import jmu.vo.Buyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BuyerMapper {

    @Select("select * from Buyer where buyerID=#{buyerID}")
    public Buyer queryByBuyerIDfrom(int buyerID);

}
