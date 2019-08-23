package com.mmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.Impl.OrderServiceImpl;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import com.sun.net.httpserver.HttpsServer;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 订单  支付
 */
@Controller
@RequestMapping("/order/")
public class OrderController {
    private static final Logger logger= LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 创建订单
     * @param
     * @param shippingId
     * @return
     */
    @RequestMapping("create.do")
    @ResponseBody
    public ServerResponse create(HttpServletRequest request,Integer shippingId){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return orderService.create(user.getId(),shippingId);
    }

    /**
     * 取消订单
     * @param
     * @param orderNo
     * @return
     */
    @RequestMapping("cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpServletRequest request,Long orderNo){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return orderService.cancel(user.getId(),orderNo);
    }

    /**
     * 订单详情detail
     * @param
     * @param orderNo
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(HttpServletRequest request,Long orderNo){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return orderService.detail(user.getId(),orderNo);
    }

    /**
     * 获取订单的商品信息
     * @param
     * @return
     */
    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpServletRequest request){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return orderService.getOrderCartProduct(user.getId());
    }

    /**
     * 订单列表
     * @param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return orderService.list(user.getId(),pageNum,pageSize);
    }


    /**
     * 支付
     * @param session
     * @param orderNo 订单号
     * @param request
     * @return
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        String path =request.getSession().getServletContext().getRealPath("upload");
        return orderService.pay(orderNo,user.getId(),path);
    }

    /**
     * 支付宝回调
     * @param request
     * @return
     */
    @RequestMapping(value = "alipay_callback.do",method = RequestMethod.POST)
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request){
        HashMap<String, String> params = Maps.newHashMap();
        Map parameterMap = request.getParameterMap();
        //迭代器
        for (Iterator iterator=parameterMap.keySet().iterator();iterator.hasNext();) {
            String name = (String) iterator.next();
            String[] values = (String[]) parameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调，sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());
        //重要 验证回调的正确性 避免重复通知
        params.remove("sign_type");
        try {
            //判断是否是该订单的支付宝请求
            boolean alipayRSACheckedV2=AlipaySignature.rsaCheckV2(
                    params,
                    Configs.getAlipayPublicKey(),"utf_8",
                    Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过！");
            }
        } catch (AlipayApiException e) {
            logger.error("支付包回调异常",e);
        }
        ServerResponse serverResponse = orderService.aliCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单支付状态
     * @param
     * @param orderNo
     * @return
     */
    @RequestMapping("query_order_pay_status.do")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(HttpServletRequest request, Long orderNo){
        //判断是否登陆
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        ServerResponse serverResponse = orderService.queryOrderPayStatus(user.getId(), orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }
}
