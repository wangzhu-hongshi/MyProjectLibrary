package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 收货地址
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService  iShippingService;

    /**
     * 添加地址
     * @param
     * @param shipping
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest request, Shipping shipping){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse delete(HttpServletRequest request,Integer shippingId){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    /**
     * 登陆状态下更新用户地址
     * @param
     * @param shipping
     * @return
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest request, Shipping shipping){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iShippingService.update(user.getId(),shipping);
    }

    /**
     * 选中查询地址
     * @param
     * @param shippingId
     * @return
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(HttpServletRequest request,Integer shippingId){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    /**
     * 查询地址列表
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }
}
