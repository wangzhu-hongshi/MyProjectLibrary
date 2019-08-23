package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface Userservice {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean registUser(User user);

    /**
     * 激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登陆 查看用户名密码
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 查看激活码
     * @param status
     * @return
     */

}
