package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 优化Servlet
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserServiceimpl usi=new UserServiceimpl();
    /**
     * 注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取表单验证码
        String check = request.getParameter("check");
        //获取系统指定的验证码
        HttpSession session = request.getSession();
        String checkcode_server =(String) session.getAttribute("CHECKCODE_SERVER");
        //删除验证码 具有一次性
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否正确
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //封装错误信息 并返回
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("不好意思，验证码错误！");
            response.setContentType("application/json;charset=utf-8");
            //转换为json
//            ObjectMapper objectMapper=new ObjectMapper();
//            String value = objectMapper.writeValueAsString(resultInfo);
//
            String value = writeValueString(resultInfo);
            response.getWriter().write(value);
            return;
        }

        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装数据
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        //UserServiceimpl usi=new UserServiceimpl();
        boolean fale=usi.registUser(user);
        //封装返回的数据
        ResultInfo resultInfo=new ResultInfo();
        if(fale){
            //注册成功
            resultInfo.setFlag(true);
            resultInfo.setErrorMsg("恭喜你，成功注册！");
        }else {
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("不好意思，用户名已存在！");
        }
        response.setContentType("application/json;charset=utf-8");
        String value = writeValueString(resultInfo);
        response.getWriter().write(value);
    }

    /**
     * 登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //UserServiceimpl userServiceimpl=new UserServiceimpl();
        User user=usi.login(username,password);
        ResultInfo resultInfo=new ResultInfo();
        if(user==null){
            //用户名和密码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        if(user!=null&&!"Y".equals(user.getStatus())){
            //还没有激活
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请激活您的激活码");
        }
        if(user!=null&&"Y".equals(user.getStatus())){
            request.getSession().setAttribute("user",user);
            resultInfo.setFlag(true);

        }
        //ObjectMapper objectMapper=new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");

        //objectMapper.writeValue(response.getOutputStream(),resultInfo);
        writeValue(resultInfo,response);
    }

    /**
     * 登陆后从session中查询user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findNoe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
//        ObjectMapper mapper=new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),user);
        writeValue(user,response);
    }

    /**
     * 退出登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();//删除session
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void actitve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取code
        String code = request.getParameter("code");
        //判断激活码是否存在 以防直接访问
        if(code!=null){
            //UserServiceimpl userServiceimpl=new UserServiceimpl();
            //调用service
            boolean falg=usi.active(code);
            String msg=null;
            if(falg){
                //激活成功
                msg="激活成功，请您<a href='http://localhost:8080/travel/'>登陆</a>";
            }else {
                //激活失败
                msg="激活失败！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

}
