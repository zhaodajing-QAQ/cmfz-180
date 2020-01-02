package com.baizhi.dao;

import com.baizhi.entity.Admin;

import java.util.List;

public interface AdminDao {
    //通过用户名查询
    Admin selectByUsername(String username);
    //查询全部
    List<Admin> selectAll();
}
