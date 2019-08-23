package com.wang.dao;

import com.wang.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {

    List<User> findAll();

    User findById(int id);

    int insert(User user);
}
