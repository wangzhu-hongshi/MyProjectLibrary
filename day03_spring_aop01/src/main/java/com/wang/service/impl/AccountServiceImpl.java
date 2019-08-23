package com.wang.service.impl;

import com.wang.service.AccountService;

public class AccountServiceImpl implements AccountService {
    public void update() {
        System.out.println("更新方法。。。。。。");
    }

    public int insert() {
        System.out.println("添加。。。。");
        return 1;
    }

    public void delete(int i) {
        System.out.println("删除了。。。。。");
    }
}
