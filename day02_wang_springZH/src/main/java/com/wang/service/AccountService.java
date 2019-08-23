package com.wang.service;

import com.wang.domain.Account;

import java.util.List;

public interface AccountService {
    /**
     * 查询所有
     * @return
     */
    List<Account> findAll();

    /**
     * 查询一个
     * @param id
     * @return
     */
    Account findById(Integer id);

    /**
     * 更新
     * @param account
     */
    void Update(Account account);

    /**
     * 保存
     * @param account
     */
    void insert(Account account);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id );



}
