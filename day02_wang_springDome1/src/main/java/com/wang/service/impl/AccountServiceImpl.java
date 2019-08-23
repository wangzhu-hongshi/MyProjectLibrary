package com.wang.service.impl;

import com.wang.dao.AccountDao;
import com.wang.dao.impl.AccountDaoImpl;
import com.wang.domain.Account;
import com.wang.service.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private AccountDao ad;
    public List<Account> findAll() {
        return ad.findAll();
    }

    public Account findById(Integer id) {
        return ad.findById(id);
    }

    public void Update(Account account) {
        ad.equals(account);
    }

    public void insert(Account account) {
        ad.insert(account);
    }

    public void delete(Integer id) {
        delete(id);
    }

    public void setAccountDao(AccountDaoImpl accountDao) {
        this.ad=accountDao;
    }
}
