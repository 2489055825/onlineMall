package jmu.mapper;


import jmu.vo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO user(account, password, identity, securityQuestion, securityAnswer)\n" +
            "VALUES (#{account}, #{password}, #{identity}, #{securityQuestion}, #{securityAnswer})")
    public boolean insert(User user);


    @Select("select * from user where userID=#{userID}")
    public User queryByUserID(int userID);


    @Select("select * from user where account=#{account}")
    public User queryByAccount(String account);

    @Select("select * from user")
    public List<User> queryAll();


}
