package jmu.mapper;

import jmu.vo.Sell;
import jmu.vo.Seller;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SellerMapper {

//    seller+ (sellerID)sell 通过商家ID找优惠  一个商家--多个优惠
    @Select("select * from seller where sellerID=#{sellerID}")
//    @Results({
//            @Result(id = true, property = "sellID", column = "sellID"),
//            @Result(property = "sellerID", column = "sellerID"),
//            @Result(property = "commodities", column = "commodities"),
//            @Result(property = "sellMoney", column = "sellMoney"),
//            @Result(property = "sells", column = "sellerID",
//                    javaType = jmu.vo.Sell.class,
//                    many = @Many(select="jmu.mapper.SellMapper.queryBySellerID",
//                            fetchType = FetchType.LAZY))
//    })
    public Seller queryBySellerID(int sellerID);

    @Update("update seller\n" +
            "set shopName=#{shopName}\n" +
            "where sellerID=#{sellerID}")
    public boolean updateBySellerID(Seller seller);

}
