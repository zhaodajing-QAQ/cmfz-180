package com.baizhi.controller;

import com.baizhi.entity.MapDto;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getSevenData")
    public List<MapDto> getSevenData(){
        List<MapDto> list = userService.selectSevenRegister();
        return list;
    }

    @RequestMapping("/getYearData")
    public List<MapDto> getYearData() {
        List<MapDto> list = userService.selectYearRegister();
        return list;
    }

    @RequestMapping("/getAddrData")
    public List<Map<String,Object>> getAddrData(){
        List<Map<String, Object>> list = userService.selectUserByAddr();
        return list;
    }
}
