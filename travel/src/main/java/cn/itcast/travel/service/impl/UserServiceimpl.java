package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.Userservice;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceimpl implements Userservice {
    private UserDaoImpl udi=new UserDaoImpl();
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public boolean registUser(User user) {
        User username = udi.findByUsername(user.getUsername());
        if(username==null){
            //用户名不存在
            //设置 激活码 和 激活状态为N
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            //保存user对象
            udi.save(user);
            //定义邮件正文
            String login="<a href='http://localhost:8080/travel/user/actitve?code="+user.getCode()+"'>点击激活_【黑马旅游网】</a>";
            //发送激活邮件
            MailUtils.sendMail(user.getEmail(),login,"激活测试邮件");
            return true;
        }else {
            //用户名存在
            return false;
        }

    }

    /**
     * 激活
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //查看是否有拥有激活码的用户
        User user=udi.findByCode(code);
        if(user!=null){
            //有 调用 修改激活状态方法
            udi.updateStatus(user);
            return true;
        }
        //没有 返回
        return false;
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {

        return udi.login(username,password);
    }




}
