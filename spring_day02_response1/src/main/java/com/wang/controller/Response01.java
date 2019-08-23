package com.wang.controller;

import com.wang.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Con")
public class Response01 {
    /**
     * 返回值是个字符串类型的
     * @param model
     * @return
     */
    @RequestMapping("/toRes")
    public String toRes(Model model){
        System.out.println("被执行了！");
        User user=new User();
        user.setUsername("王诸");
        user.setPassword(123);
        user.setAge("男");
        model.addAttribute("user",user);
        return "success";
    }
    /**
     * 返回值是void类型的 默认使用的是方法名.jsp
     * 解决方法可以使用 转发 或者 重定向 或者直接响应
     */


    /**
     * 返回值是 ModelAndView
     * 可以把bean对象存到request域中
     * 可以直接设置返回到某某jsp 他会调用视图解析器
     * @return
     */
    @RequestMapping("/toModelAndView")
    public ModelAndView toModelAndView(){
        ModelAndView mv=new ModelAndView();
        System.out.println("toModelAndView被执行了！");
        User user=new User();
        user.setUsername("宏诗");
        user.setPassword(123);
        user.setAge("男");
        mv.addObject(user);
        mv.setViewName("success");
        return mv;
    }


    /**
     * 接受异步请求穿过来的数据 转化json数据
     * @param user
     * @return
     */
    @RequestMapping("/testJson")
    public @ResponseBody
    User testJson(@RequestBody User user){

        System.out.println("testJson执行了");
        user.setUsername("宏诗");
        user.setPassword(123);
        user.setAge("男");
        return user;
    }
}
