package com.baizhi.service.impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapDto;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<MapDto> selectSevenRegister() {
        //查询数据
        List<MapDto> userDtos = userDao.selectSevenRegister();
        return userDtos;
    }

    @Override
    public List<MapDto> selectYearRegister() {
        //查询数据
        List<MapDto> userDtos = userDao.selectYearRegister();
        return userDtos;
    }

    @Override
    public List<Map<String, Object>> selectUserByAddr() {
        List<Map<String, Object>> list = new ArrayList<>();
        //查询数据
        List<MapDto> mapDtos = userDao.selectUserByAddr();
        for (MapDto mapDto : mapDtos) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",mapDto.getName());
            map.put("value",mapDto.getValue());
            list.add(map);
        }
        return list;
    }

    /*
    *   注册
    * */

    @Override
    public int addUser(User user) {
        int i = userDao.insertSelective(user);
        return i;
    }
}
