package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /*
    *   登录
    *   参数：用户名   密码   验证码
    * */
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username, String password, String code,HttpSession session){
        //判断验证码是否正确
        String validationCode = (String) session.getAttribute("validationCode");
        if (validationCode.equals(code)){
            //验证码正确查询当前输入用户
            Admin admin = adminService.login(username, password);
            if (admin!=null){
                return "";
            }else{
                return "用户名或密码错误";
            }
        }
        return "验证码错误";
    }

    @RequestMapping("/outPoi")
    @ResponseBody
    public void outPoi(HttpServletResponse response) throws IOException {
        adminService.outPoi(response);
    }
}
