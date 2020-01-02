package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AdminService {
    //登录
    public Admin login(String username,String password);
    //导出管理员信息到excel
    public void outPoi(HttpServletResponse response) throws IOException;
}
