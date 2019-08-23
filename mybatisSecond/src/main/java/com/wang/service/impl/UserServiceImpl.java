package com.wang.service.impl;

import com.wang.dao.IUserDao;
import com.wang.service.UserService;

public class UserServiceImpl implements UserService {

    private IUserDao iUserDao;

    public IUserDao getiUserDao() {
        return iUserDao;
    }

    public void setiUserDao(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }
}
