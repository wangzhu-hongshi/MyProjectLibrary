package com.wang.Dao;

import com.wang.domain.User;

import java.util.List;

/**
 * 用户持久层接口
 */
public interface IUserDao {
    /**
     * 查询所有操作
     * @return
     */
    List<User> findAll();




}
