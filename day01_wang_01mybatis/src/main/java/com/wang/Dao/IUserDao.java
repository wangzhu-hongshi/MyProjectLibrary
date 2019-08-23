package com.wang.Dao;

import com.wang.domain.QueryVo;
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

    /**
     * 保存用户操作
     * @param user
     */
    void save(User user);

    List<User> findByName(String name);

    /**
     * 条件查询
     * @return
     */
    List<User> findByQ(QueryVo queryVo);

}
