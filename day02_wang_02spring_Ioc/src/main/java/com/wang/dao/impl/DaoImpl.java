package com.wang.dao.impl;

import com.wang.dao.Dao;
import org.springframework.stereotype.Repository;

@Repository
public class DaoImpl implements Dao {
    public void find() {
        System.out.println("被你找到了！！！");
    }
}
