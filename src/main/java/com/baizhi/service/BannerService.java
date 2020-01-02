package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页查询
    public Map<String,Object> queryAll(Integer page,Integer rows);
    //添加
    public void addBanner(Banner banner);
    //修改
    public void changeBanner(Banner banner);
    //删除
    public void removeBanner(String[] ids);
    //上传
    public String bannerUpload(MultipartFile img, HttpSession session, String id);
    //文件导出
    public void easyPoi(HttpServletResponse response,HttpSession session);
    //查询全部
    public List<Banner> queryAll();
}
