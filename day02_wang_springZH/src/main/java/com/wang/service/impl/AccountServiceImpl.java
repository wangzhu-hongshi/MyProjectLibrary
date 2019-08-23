package com.wang.service.impl;

import com.wang.dao.AccountDao;
import com.wang.dao.impl.AccountDaoImpl;
import com.wang.domain.Account;
import com.wang.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("accountService")
public class AccountServiceImpl implements AccountService{
    @Autowired
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

}
