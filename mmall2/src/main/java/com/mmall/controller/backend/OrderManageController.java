package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
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
 * 订单后台
 *
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //1.判断登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员,没有权限");
//        }
        return iOrderService.mangaeList(pageNum,pageSize);
    }

    /**
     * 查看订单详情
     * @param session
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse dateil(HttpSession session,Long orderNo){
//        //1.判断登陆
//        User user= (User)session.getAttribute(Const.CURRENT_USER);
//        if(user==null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员,没有权限");
//        }
        return iOrderService.mangaeDetail(orderNo);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search(HttpSession session,Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //1.判断登陆
//        User user= (User)session.getAttribute(Const.CURRENT_USER);
//        if(user==null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员,没有权限");
//        }
        return iOrderService.mangaeSearch(orderNo,pageNum,pageSize);
    }

    /**
     * 发货
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse sendGoods(HttpSession session,Long orderNo){
        //1.判断登陆
//        User user= (User)session.getAttribute(Const.CURRENT_USER);
//        if(user==null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员,没有权限");
//        }
        return iOrderService.manageSendGoods(orderNo);
    }
}
