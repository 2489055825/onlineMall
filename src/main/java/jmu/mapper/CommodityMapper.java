package jmu.mapper;

import jmu.vo.Commodity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityMapper {

    @Select("select * from commodity where commodityName=#{commodityName}")
    public List<Commodity> queryByCommodityName(String commodityName);

    @Select("select * from commodity where commodityID=#{commodityID}")
    public List<Commodity> queryByCommodityID(String commodityID);

    @Select("select * from commodity")
    public List<Commodity> queryAll();
    @Select("select * from commodity where commodityID=#{commodityID}")
    public Commodity queryByCommodityID(int commodityID);

    @Select("select * from commodity where sellerID=#{sellerID}")
    public List<Commodity> queryBySellerID(int sellerID);

    @Update("update commodity\n" +
            "set orderItemID=#{orderItemID},sellerID=#{sellerID}, commodityName=#{commodityName}," +
            "commodityPhoto=#{commodityPhoto},\n" +
            "commodityPrice=#{commodityPrice}, inventory=#{inventory}" +
            "where commodityID=#{commodityID}")
    public boolean updateByCommodityID(int commodityID);

    @Delete("DELETE\n" +
            "FROM commodity\n" +
            "WHERE commodityID = #{commodityID}")
    public boolean deleteByCommodityID(int commodityID);



@Insert("INSERT INTO commodity(sellerID, commodityName, commodityPhoto, commodityPrice, inventory)\n" +
        "VALUES (#{sellerID}, #{commodityName}, #{commodityPhoto}, #{commodityPrice}, #{inventory})")
    public boolean insert(Commodity commodity);


    @Select("select * from commodity where sellerID=#{sellerID}")
    public List<Commodity> queryBySellerIDfrom(int sellerID);
}
