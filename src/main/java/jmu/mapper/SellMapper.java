package jmu.mapper;

import jmu.vo.Sell;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SellMapper {

    //通过sellID找本身＋seller
    @Select("select * from sell where sellerID=#{sellerID}") //升序排列？？
//    @Results({
//            @Result(id = true, property = "sellID", column = "sellID"),
//            @Result(property = "sellerID", column = "sellerID"),
//            @Result(property = "sellCondition", column = "sellCondition"),
//            @Result(property = "sellMoney", column = "sellMoney"),
//            @Result(property = "seller", column = "sellerID",
//            javaType = jmu.vo.Seller.class,
//            one = @One(select="jmu.mapper.SellerMapper.queryBySellerID",
//            fetchType = FetchType.LAZY))
//    })
    public List<Sell> queryBySellerID(int sellerID);


    @Delete("DELETE\n" +
            "FROM sell\n" +
            "WHERE sellID = #{sellID}")
    public boolean deleteBySellID(int sellID);


    @Insert("INSERT INTO sell(sellerID, sellCondition, sellMoney)\n" +
            "VALUES (#{sellerID}, #{sellCondition}, #{sellMoney})")
    public boolean insert(Sell sell);


}
