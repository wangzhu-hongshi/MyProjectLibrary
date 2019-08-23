package com.wang.dao;

import com.wang.domain.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 持久层的接口类
 */
@Repository
public interface IAccountDao {
    /**
     * 查询所有
     * @return
     */
    @Select(value = "select * from account")
    List<Account> findAll();

    /**
     * 保存用户
     * @param account
     */
    @Update("insert into accoun(name,money) values(#{name},#{money})")
    void saveAccount(Account account);

}
