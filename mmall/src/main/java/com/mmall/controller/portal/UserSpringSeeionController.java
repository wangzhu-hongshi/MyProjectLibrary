package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 使用Spring session 框架 实现单点登陆
 */
@Controller
@RequestMapping("/user/springsession/")
public class UserSpringSeeionController {
    @Autowired
    private IUserService iUserService;
    /**
     * 用户登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse response){


        ServerResponse login = iUserService.login(username, password);
        if(login.isSuccess()){
            //登陆成功 将用户对象存入session中 key是定义好的常量
            session.setAttribute(Const.CURRENT_USER,login.getData());
            //使用分布式集群  采用 Cookie技术 和Redis技术
//            CookieUtil.writeloginToken(response,session.getId());
//            //
//            RedisShardedPoolUtil.setEx(session.getId(),JsonUtil.obj2String(login.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

        }
        return login;
    }

    /**
     * 退出登陆（登出功能）
     * 删除登陆功能中保存的session的key
     * @param
     * @return
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//        String token = CookieUtil.readLoginToken(request);
//        CookieUtil.delloginToken(response,request);
//        RedisShardedPoolUtil.del(token);
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess("退出成功！");
    }



    /***
     * 获取登陆的用户
     * @param
     * @return
     */
    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request,HttpSession session){
        //直接从session中获取 登陆操作保存的的session对象
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccess(user);
        }
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
        return ServerResponse.createBySuccess(user);
    }


}
