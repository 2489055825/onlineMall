package jmu.mapper;

import jmu.vo.Receiver;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReceiverMapper {

    @Select("select * from receiver where BuyerID=#{BuyerID}")
    @Results({
            @Result(id = true, property = "receiverID", column = "receiverID"),
            @Result(property = "buyerID", column = "buyerID"),
            @Result(property = "countyID", column = "countyID"),
            @Result(property = "addressDetail", column = "addressDetail"),
            @Result(property = "receiverName", column = "receiverName"),

            @Result(property = "ordersList", column = "receiverID",
                    javaType = List.class,
                    many = @Many(select="jmu.mapper.OrdersMapper.queryByReceiverIDfrom",
                            fetchType = FetchType.LAZY)),
            @Result(property = "buyer", column = "buyerID",
                    javaType = jmu.vo.Buyer.class,
                    one = @One(select="jmu.mapper.BuyerMapper.queryByBuyerIDfrom",
                            fetchType = FetchType.LAZY)),
            @Result(property = "county", column = "countyID",
                    javaType = jmu.vo.County.class,
                    one = @One(select="jmu.mapper.CountyMapper.queryByCountyIDfrom",
                            fetchType = FetchType.LAZY))
    })
    public List<Receiver> queryByBuyerID(int BuyerID);

    @Delete("DELETE\n" +
            "FROM receiver\n" +
            "WHERE receiverID = #{receiverID}")
    public boolean deleteByReceiverID(int receiverID);

    @Insert("INSERT INTO receiver(countyID, buyerID, addressDetail, receiverName)\n" +
            "VALUES (#{countyID}, #{buyerID}, #{addressDetail}, #{receiverName})")
    public boolean insert(Receiver receiver);

    @Select("select * from receiver where receiverID=#{receiverID}")
    public Receiver queryByReceiverIDfrom(int receiverID);

}
