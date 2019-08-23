package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法的分发
        //获取uRI路径
        String uri = req.getRequestURI();
        System.out.println(uri);
        //获取方法名
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(methodName);
        //调用方法 使用反射机制
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 直接将传入的对像 序列化为json 直接返回到客户端
     * @param obj
     * @param response
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将对象序列化为 json 返回
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public  String writeValueString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
