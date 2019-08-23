package com.wang.dao;

import com.wang.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 注解实现曾删改查功能
 */
public interface IUserDao  {
    @Select("select * from user ")
    List<User> findAll();

    @Insert("insert into user (username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    public void insert(User user);

    @Update("update user set username=#{username},birthday=#{birthday},sex=#{sex},address=#{address} where id=#{id}")
    public void update(User user);

    @Delete("delete from user where id=#{id}")
    public void delete(Integer id);

    @Select("select count(*) from user")
    public int findTotal();

    @Select("select * from user where username like #{username}")
    List<User> findByName(String username);
}
