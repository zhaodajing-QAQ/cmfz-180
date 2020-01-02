package com.baizhi.dao;

import com.baizhi.entity.MapDto;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //分页查询
    List<User> selectAllUser(@Param("start")Integer start,@Param("rows")Integer rows);
    //查询最近七天注册的用户量
    List<MapDto> selectSevenRegister();
    //查询1-12月注册的用户量
    List<MapDto> selectYearRegister();
    //查询用户通过地址
    List<MapDto> selectUserByAddr();
}