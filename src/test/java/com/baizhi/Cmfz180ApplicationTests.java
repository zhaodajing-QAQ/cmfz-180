package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.service.AdminService;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Cmfz180ApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminService adminService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private BannerService bannerService;

    @Test
    public void contextLoads() {
        Admin admin = adminDao.selectByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void testLogin(){
        Admin login = adminService.login(null, null);
        System.out.println(login);
    }

    @Test
    public void bannerTest1(){
        Banner banner = new Banner("1", "图1", "123112", new Date(), "展示", null);
        bannerDao.bannerAdd(banner);
        System.out.println("-----invoke------");
    }

    @Test
    public void bannerTest2(){
        List<Banner> banners = bannerDao.selectAll(0,1);
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

    @Test
    public void bannerTest3(){
        Banner banner = new Banner();
        banner.setId("1");
        banner.setImg("");
        banner.setStatus("不展示");
        bannerService.changeBanner(banner);
        System.out.println("------invoke------");
    }

    @Test
    public void bannerTest4(){
        String[] ids = {"1"};
        bannerDao.bannerDelete(ids);
        System.out.println("invoke");
    }

    @Test
    public void test5(){
        Map<String, Object> map = bannerService.queryAll(2, 2);
        List<Banner> banners = (List<Banner>) map.get("rows");
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }
}
