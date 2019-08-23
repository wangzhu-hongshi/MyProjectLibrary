package com.wang.controller;

import com.wang.controller.exception.SysException;
import com.wang.domain.Account;
import com.wang.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.sasl.SaslException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * web层
 *
 */
@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    public AccountService service;

    /**
     * 查询方法
     * @param model
     * @return
     */
    @RequestMapping("/SSM")
    public String ControllerSSM(Model model) throws SysException {
        try{
            System.out.println("欢迎访问！表现层");
            List<Account> all = service.findAll();
            model.addAttribute("list",all);
            return "success";
        }catch (Exception e){
            throw new SysException("查询出现异常！");
        }

    }
    @RequestMapping("save")
    public void save(Account account, HttpServletRequest request, HttpServletResponse response) throws SysException {
        try{
            service.saveAccount(account);
            response.sendRedirect(request.getContextPath()+"/User/SSM");
            return;
        }catch(Exception e){
            throw new SysException("保存操作出现异常！有可能是sql语句错误！");
        }

    }

}
