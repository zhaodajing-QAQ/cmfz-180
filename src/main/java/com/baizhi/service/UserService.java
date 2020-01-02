package com.baizhi.service;

import com.baizhi.entity.MapDto;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询最近7天用户注册量
    List<MapDto> selectSevenRegister();
    //查询1-12月用户注册量
    List<MapDto> selectYearRegister();
    //查询各地用户
    List<Map<String,Object>> selectUserByAddr();
    //注册
    int addUser(User user);
}
