package com.wang.service;

import com.wang.domain.Account;

import java.util.List;

/**
 * 业务层接口
 */
public interface AccountService {
    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();

    /**
     * 保存用户
     * @param account
     */
    void saveAccount(Account account);
}
