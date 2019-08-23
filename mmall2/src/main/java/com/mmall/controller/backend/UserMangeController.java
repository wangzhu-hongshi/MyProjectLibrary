package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员 用户
 */
@Controller
@RequestMapping("/manage/user")
public class UserMangeController {

    @Autowired
    private IUserService iUserService;

    /**
     * 管理员登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
        ServerResponse<User> reponse = iUserService.login(username, password);
        if(reponse.isSuccess()){
            User user = reponse.getData();
            if(user.getRole()== Const.Role.ROLE_ADMIN){
                //说明登陆的是管理员
                CookieUtil.writeloginToken(httpServletResponse,session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(user),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                return reponse;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登陆");
            }
        }
        return reponse;
    }
}
