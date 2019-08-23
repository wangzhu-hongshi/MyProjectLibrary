package com.wang.service.impl;

import com.wang.dao.IAccountDao;
import com.wang.domain.Account;
import com.wang.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层的实现类
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    public IAccountDao iAccountDao;
    /**
     * 查询所有的方法
     * @return
     */
    @Override
    public List<Account> findAll() {
        System.out.println("业务层：查询所有");
        return iAccountDao.findAll();
    }

    /**
     * 保存账户的方法
     * @param account
     */
    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层：保存账户");
        iAccountDao.saveAccount(account);
    }
}
