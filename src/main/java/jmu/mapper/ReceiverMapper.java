package jmu.mapper;

import jmu.vo.Receiver;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReceiverMapper {

    @Select("select * from receiver where BuyerID=#{BuyerID}")
    public List<Receiver> queryByBuyerID(int BuyerID);

    @Delete("DELETE\n" +
            "FROM receiver\n" +
            "WHERE receiverID = #{receiverID}")
    public boolean deleteByReceiverID(int receiverID);

    @Insert("INSERT INTO receiver(countyID, buyerID, addressDetail, receiverName)\n" +
            "VALUES (#{countyID}, #{buyerID}, #{addressDetail}, #{receiverName})")
    public boolean insert(Receiver receiver);

}
