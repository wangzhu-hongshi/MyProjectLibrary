package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 用户注册
     * @param username
     * @return
     */
    User findByUsername(String username);
    void save(User user);

    /**
     * 激活
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 修改激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
