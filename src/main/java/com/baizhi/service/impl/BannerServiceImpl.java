package com.baizhi.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        /**   计算起始
         *   page: 当前页
         *   rows: 数据
         *   total: 总页数
         *   records: 总条数
         **/
        Integer start = (page-1)*rows;
        //数据
        List<Banner> banners = bannerDao.selectAll(start, rows);
        //总条数
        int records = bannerDao.selectCount();
        //总页数
        int total = records % rows == 0 ? records / rows : records / rows + 1;

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",banners);
        map.put("total",total);
        map.put("records",records);
        return map;
    }

    @Override
    @Transactional
    public void addBanner(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreate_date(new Date());
        bannerDao.bannerAdd(banner);
    }

    @Override
    @Transactional
    public void changeBanner(Banner banner) {
        bannerDao.bannerUpload(banner);
    }

    @Override
    @Transactional
    public void removeBanner(String[] ids) {
        bannerDao.bannerDelete(ids);
    }

    @Transactional
    public String bannerUpload(MultipartFile img, HttpSession session, String id){
        //1.获取upload的路径
        String realPath = session.getServletContext().getRealPath("/upload");
        //2.判断文件夹是否存在
        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdirs();
        }
        //3.获取文件名
        String filename = img.getOriginalFilename();
        //4.上传
        try {
            img.transferTo(new File(realPath,filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.更换数据库中的路径
        if (filename.equals("")){
            filename=null;
        }
        Banner banner = new Banner();
        banner.setId(id);
        banner.setImg(filename);
        bannerDao.bannerUpload(banner);
        return "上传成功！";
    }

    @Override
    public void easyPoi(HttpServletResponse response,HttpSession session) {
        //设置下载方式
        response.setHeader("content-disposition","attachment;filename=banner.xls");
        //查询数据
        List<Banner> banners = bannerDao.selectAllBanner();
        //设置图片路径
        String realPath = session.getServletContext().getRealPath("upload");
        for (Banner banner : banners) {
            banner.setImg(realPath+"/"+banner.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图信息","轮播图"),
                Banner.class, banners);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> queryAll() {
        return bannerDao.selectAllBanner();
    }
}
