package jmu.mapper;

import jmu.vo.Commodity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommodityMapper {

    @Select("select * from commodity where commodityName like #{commodityName}")
    @Results({
            @Result(id = true, property = "commodityID", column = "commodityID"),
            @Result(property = "orderItemID", column = "orderItemID"),
            @Result(property = "sellerID", column = "sellerID"),
            @Result(property = "commodityName", column = "commodityName"),
            @Result(property = "commodityPhoto", column = "commodityPhoto"),
            @Result(property = "commodityPrice", column = "commodityPrice"),
            @Result(property = "inventory", column = "inventory"),


            @Result(property = "seller", column = "sellerID",
                    javaType = jmu.vo.Seller.class,
                    one = @One(select="jmu.mapper.SellerMapper.queryBySellerIDfrom",
                            fetchType = FetchType.LAZY)),
//            @Result(property = "orderItemList", column = "receiverID",
//                    javaType = List.class,
//                    many = @Many(select="jmu.mapper.OrdersMapper.queryByReceiverIDfrom",
//                            fetchType = FetchType.LAZY))
    })
    public List<Commodity> queryByCommodityName(String commodityName);


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
