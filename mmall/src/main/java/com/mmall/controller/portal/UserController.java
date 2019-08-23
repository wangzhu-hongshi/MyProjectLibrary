package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 前台用户接口
 */
@Controller
@RequestMapping("/user/")
public class UserController {
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
//            session.setAttribute(Const.CURRENT_USER,login.getData());
            //使用分布式集群  采用 Cookie技术 和Redis技术
            CookieUtil.writeloginToken(response,session.getId());
            //
            RedisShardedPoolUtil.setEx(session.getId(),JsonUtil.obj2String(login.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

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
    public ServerResponse<String> logout(HttpServletRequest request,HttpServletResponse response){
        String token = CookieUtil.readLoginToken(request);
        CookieUtil.delloginToken(response,request);
        RedisShardedPoolUtil.del(token);
        return ServerResponse.createBySuccess("退出成功！");
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /**
     * 用户名和email的校验
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "checkValid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        ServerResponse serverResponse = iUserService.checkValid(str, type);
        return serverResponse;
    }

    /***
     * 获取登陆的用户
     * @param
     * @return
     */
    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request){
        //直接从session中获取 登陆操作保存的的session对象
//        User user=(User) session.getAttribute(Const.CURRENT_USER);
//        if(user!=null){
//            return ServerResponse.createBySuccess(user);
//        }
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return ServerResponse.createBySuccess(user);
    }

    /**
     * 找回密码的问题
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 验证找回密码的答案
     * @param username
     * @param question
     * @param answer 找回密码的答案
     * @return
     */
    @RequestMapping(value = "forget_check_Answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 在未登陆状态下重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_Rest_Password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }
    /**
     * 在已登陆状态下重置密码
     * @param
     * @param passwordOld 旧的密码
     * @param passwordNew 新的密码
     * @return
     */
    @RequestMapping(value = "Rest_Password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpServletRequest request,String passwordOld,String passwordNew){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 登陆状态更新用户
     * @param
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> update_information(HttpServletRequest request,User user){
        //1.校验用户是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User currentUser = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(currentUser==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        //userId是不能修改的
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        //调用业务层
        ServerResponse<User> res = iUserService.updateInformation(user);
        //判断是否成功
        if(res.isSuccess()){
            RedisShardedPoolUtil.setEx(loginToken,JsonUtil.obj2String(res.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return res;
    }

    /**
     * 再登陆状态下 查询用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "get_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> get_information(HttpServletRequest request){
        //1.校验用户是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iUserService.getInformation(user.getId());
    }
}
