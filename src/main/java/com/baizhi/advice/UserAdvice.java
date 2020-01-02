package com.baizhi.advice;

import com.baizhi.entity.MapDto;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Aspect
public class UserAdvice {
    @Autowired
    private UserService userService;

    @Pointcut(value = "execution(* com.baizhi.service.UserService.add*(..))")
    public void expression(){}

    @After("expression()")
    public void after(){
        List<MapDto> mapDtos = userService.selectSevenRegister();
        List<MapDto> mapDtos1 = userService.selectYearRegister();


        /*List<Integer> day = new ArrayList<Integer>();
        for (MapDto mapDto : mapDtos) {
            day.add(mapDto.getValue());
        }

        List<Integer> month = new ArrayList<Integer>();
        for (MapDto mapDto : mapDtos1) {
            month.add(mapDto.getValue());
        }*/


        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-f68d6b29c552443e9fc0725979b37a51");
        goEasy.publish("day", mapDtos.toString());
        goEasy.publish("month", mapDtos1.toString());
    }
}
