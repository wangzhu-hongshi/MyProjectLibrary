package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;

import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询用户名
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        try{
            String sql="select * from tab_user where username=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        }catch (Exception e){

            return null;
        }

    }

    /**
     *用户注册 添加
     * @param user
     */
    @Override
    public void save(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int update = template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    /**
     * 查看激活码
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        try{
            String sql="select * from tab_user where code=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
            return user;
        }catch (Exception e){

            return null;
        }
    }

    /**
     * 修改指定用户的状态码
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status= 'Y' where uid=?";
        template.update(sql,user.getUid());
    }

    @Override
    public User login(String username, String password) {
        try{
            String sql="select * from tab_user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
            return user;
        }catch (Exception e){

            return null;
        }
    }
}
