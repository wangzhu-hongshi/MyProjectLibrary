package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService<T> {
    ServerResponse<T> login(String username, String password);

    ServerResponse<T> register(User user);

    ServerResponse<T> checkValid(String str,String type);

    ServerResponse<T> selectQuestion(String username);

    ServerResponse<T> checkAnswer(String username,String password,String Answer);

    ServerResponse<T> forgetResetPassword(String username,String passwordNew,String forgetToken);

    ServerResponse<T> resetPassword(String passwordOld,String passwordNew,User user);

    ServerResponse<T> updateInformation(User user);

    ServerResponse<User> getInformation(Integer integer);

    ServerResponse<String> checkAdminRole(User user);
}
