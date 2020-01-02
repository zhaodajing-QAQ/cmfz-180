package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    //展示全部
    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page,Integer rows){
        //调用业务
        Map<String, Object> map = bannerService.queryAll(page,rows);
        return map;
    }

    //添加图片
    @RequestMapping("/oper")
    @ResponseBody
    public Map<String,String> oper(String oper,Banner banner,String[] id){
        Map<String, String> map = new HashMap<>();
        if ("add".equals(oper)){
            bannerService.addBanner(banner);
            map.put("id",banner.getId());
            map.put("msg","添加成功");
        }
        if ("del".equals(oper)){
            bannerService.removeBanner(id);
            map.put("msg","删除成功");
        }
        if ("edit".equals(oper)){
            if (banner.getImg().equals("")){
                banner.setImg(null);
                bannerService.changeBanner(banner);
                map.put("id",null);
                map.put("msg","修改成功");
            }else {
                map.put("id",banner.getId());
                bannerService.changeBanner(banner);
                map.put("msg","修改成功");
            }
        }
        return map;
    }

    @RequestMapping("/bannerUpload")
    @ResponseBody
    public String upload(MultipartFile img, HttpSession session,String id){
        return bannerService.bannerUpload(img,session,id);
    }

    @RequestMapping("/easyPoi")
    public void easyPoi(HttpServletResponse response,HttpSession session){
        bannerService.easyPoi(response,session);
    }
}
