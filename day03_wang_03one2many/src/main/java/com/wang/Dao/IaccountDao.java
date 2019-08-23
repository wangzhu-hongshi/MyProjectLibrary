package com.wang.Dao;

import com.wang.domain.Account;

import java.util.List;

/**
 *账户
 */
public interface IaccountDao {
    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();
}
