package com.wang.dao;

import com.wang.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 注解实现曾删改查功能
 */
public interface IUserDao  {
    @Select("select * from user ")

    @Results(id="userMap",value={
            @Result(id=true,property ="userId",column="id"),
            @Result(property ="userName",column = "username"),
            @Result(property ="userBirthday",column = "birthday"),
            @Result(property ="userSex",column = "sex"),
            @Result(property ="userAddress",column = "address"),
            //多对一使用延迟操作
            @Result(property = "accounts",column = "id",many = @Many(select = "com.wang.dao.IAccountDao.findById",fetchType = FetchType.LAZY))

    })
    List<User> findAll();

    @Select("select * from user where id = #{uid} ")
    @ResultMap("userMap")
    User findById(Integer uid);

}
