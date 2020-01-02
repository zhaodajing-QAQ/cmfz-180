package com.baizhi;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.MapDto;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    public void test1(){
        List<MapDto> mapDtos = userDao.selectUserByAddr();
        System.out.println(mapDtos);
    }

    @Test
    public void test2(){
        User user = new User();
        user.setId("19");
        user.setAddress("北京");
        user.setCreateDate(new Date());
        int i = userService.addUser(user);
        System.out.println(i);
    }

    @Test
    public void tset4(){
        User user = new User();
        user.setId("25");
        userDao.deleteByPrimaryKey("25");
    }
}
