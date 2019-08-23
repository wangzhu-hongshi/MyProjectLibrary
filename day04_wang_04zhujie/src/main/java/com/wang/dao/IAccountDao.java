package com.wang.dao;

import com.wang.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {
    @Select("select * from account")
    @Results(id="accountMap",value={
            @Result(id=true,property="id",column="id"),
            @Result(property ="uid",column = "uid"),
            @Result(property="money",column = "money"),
            //一对一的使用注解
            @Result(property = "user",column = "uid",one=@One(select = "com.wang.dao.IUserDao.findById",fetchType = FetchType.EAGER))
    })
    List<Account> findAll();

    @Select("select * from account where uid=#{id}")
    Account findById(Integer id);
}
