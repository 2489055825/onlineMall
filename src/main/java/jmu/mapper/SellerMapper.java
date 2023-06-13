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
    @Select("select * from seller where sellerID=#{sellerID}")
    @Results({
            @Result(id = true, property = "sellerID", column = "sellerID"),
            @Result(property = "shopName", column = "shopName"),

            @Result(property = "orderItemList", column = "sellerID",
                    javaType = List.class,
                    many = @Many(select="jmu.mapper.OrdersMapper.queryBySellerIDfrom",
                            fetchType = FetchType.LAZY)),
            @Result(property = "sellList", column = "sellerID",
                    javaType = List.class,
                    many = @Many(select="jmu.mapper.SellMapper.queryBySellerIDfrom",
                            fetchType = FetchType.LAZY)),
            @Result(property = "commodityList", column = "sellerID",
                    javaType = List.class,
                    many = @Many(select="jmu.mapper.CommodityMapper.queryBySellerIDfrom",
                            fetchType = FetchType.LAZY))
    })
    public Seller queryBySellerID(int sellerID);

    @Update("update seller\n" +
            "set shopName=#{shopName}\n" +
            "where sellerID=#{sellerID}")
    public boolean updateBySellerID(Seller seller);

    @Select("select * from seller where sellerID=#{sellerID}")
    public Seller queryBySellerIDfrom(int sellerID);
}
