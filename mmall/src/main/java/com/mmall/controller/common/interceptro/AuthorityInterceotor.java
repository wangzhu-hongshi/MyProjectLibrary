package com.mmall.controller.common.interceptro;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 拦截器
 */
@Slf4j
public class AuthorityInterceotor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("preHandle");

        HandlerMethod handlerMethod=(HandlerMethod) handler;//处理对象
        //获取请求的方法名
        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();//拿到拦截到的方法名
        String className = handlerMethod.getBean().getClass().getSimpleName();//拿到拦截到的类名
        //解析参数，具体的参数key以及value是什么，打印日志

        StringBuilder builder=new StringBuilder();
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();//拿到参数的map集合
        Iterator<String> iterator = parameterMap.keySet().iterator();//遍历集合的key
        while (iterator.hasNext()){
            String mapKey = iterator.next();
            String[] strings = parameterMap.get(mapKey);
            String mapVlaue = Arrays.toString(strings);
            builder.append(mapKey).append("=").append(mapVlaue);
         }
        //放过登陆接口
        if(StringUtils.equals(className,"UserMangeController")&&StringUtils.equals(methodName,"login")){
            log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
            return true;
        }

        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:",className,methodName,builder.toString());

        //判断是否登陆
        User user=null;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);//拿到登陆时存入cookie的loginToken
        if(StringUtils.isNotEmpty(loginToken)) {
            //去reids里拿 loginToken 并把 json数据转化为user对象
            user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        }
            if(user==null||(!user.getRole().equals(Const.Role.ROLE_ADMIN))){
                //返回 false。既不会调用controller 里的方法
                //重置
                httpServletResponse.reset();//qeelynote 这里要添加reset，否则报异常 getWriter()
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                //
                PrintWriter out = httpServletResponse.getWriter();
                Map resultMap= Maps.newHashMap();
                if(user==null){
                    //上传由于富文本控件要求，要特殊处理返回值 这里面区分是否登陆以及是否有权限
                    if(StringUtils.equals(className,"ProductManageController")&&StringUtils.equals(methodName,"richtextImgUpload")){
                        resultMap.put("success",false);
                        resultMap.put("msg","请登录管理员");
                        out.print(JsonUtil.obj2String(resultMap));
                    }else {
                        out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户没登陆")));
                    }
                }else {
                    if(StringUtils.equals(className,"ProductManageController")&&StringUtils.equals(methodName,"richtextImgUpload")){
                        resultMap.put("success",false);
                        resultMap.put("msg","无访问权限");
                        out.print(JsonUtil.obj2String(resultMap));
                    }else {
                        out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，无权限")));
                    }
                }
                out.flush();
                out.close();
                return false;
            }
        //2.校验是否是管理员
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        log.info("afterCompletion");

    }
}
