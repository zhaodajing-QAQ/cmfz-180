package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        /*
        *   page 起始
        *   rows 数据
        *   records 总条数
        *   total 总页数
        * */
        int start = (page-1)*rows;
        List<Article> list = articleDao.selectAll(start, rows);
        int records = articleDao.selectRecords();
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",records);
        map.put("total",total);
        return map;
    }

    @Override
    @Transactional
    public int addArticle(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int i = articleDao.insertSelective(article);
        return i;
    }

    @Override
    @Transactional
    public int changeArticle(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        return i;
    }

    @Override
    @Transactional
    public int removeArticle(String[] ids) {
        int i = articleDao.deleteByPrimaryKey(ids);
        return i;
    }

    @Override
    @Transactional
    public Map<String, Object> uploadImg(MultipartFile img, HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //获取绝对路径
        String realPath = session.getServletContext().getRealPath("/article/img");
        //文件夹不存在就创建
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String originalFilename = img.getOriginalFilename();
        String filename = new Date().getTime() + "_" + originalFilename;
        //上传
        try {
            img.transferTo(new File(realPath, filename));
            /*
             * {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
             * {"error":0, "url":"http://localhost:80/cmfz/upload/img/xxx.png" }
             * */
            map.put("error",0);
            //获取url并拼接
            String scheme = request.getScheme();            //http
            //PC-20190718ZLAM/192.168.1.156
            String host = InetAddress.getLocalHost().toString().split("/")[1];  //host
            int serverPort = request.getServerPort();       //port
            String contextPath = request.getContextPath();  //项目名
            String url = scheme + "://" + host + ":" + serverPort + contextPath + "/article/img/" + filename;
            map.put("url",url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> getAllImgs(HttpSession session, HttpServletRequest request) {
          /*
    *
    * {
    "moveup_dir_path": "",
    "current_dir_path": "",
    "current_url": "/ke4/php/../attached/",
    "total_count": 5,
    "file_list": [
        {
            "is_dir": false,
            "has_file": false,
            "filesize": 208736,
            "dir_path": "",
            "is_photo": true,
            "filetype": "jpg",
            "filename": "1241601537255682809.jpg",
            "datetime": "2018-06-06 00:36:39"
        }
     ]
}
    * */
        Map<String, Object> map = new HashMap<>();
        //拿到所有图片文件夹
        String realPath = session.getServletContext().getRealPath("/article/img");
       // 获取全部图片
        String[] imgs = new File(realPath).list();
        //遍历添加所有图片信息
        List<Object> list = new ArrayList<>();
        for (String img : imgs) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("is_dir",false);
            hashMap.put("has_file",false);
            File file = new File(realPath, img);
            long length = file.length();
            hashMap.put("filesize",length);
            hashMap.put("dir_path","");
            hashMap.put("is_photo",true);
            String extension = FilenameUtils.getExtension(img);
            hashMap.put("filetype",extension);
            hashMap.put("filename",img);
            String s = img.split("_")[0];
            Long aLong = Long.valueOf(s);
            Date date = new Date(aLong);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-yy hh:mm:ss");
            String format = simpleDateFormat.format(date);
            hashMap.put("datetime",format);
            list.add(hashMap);
        }
        map.put("file_list",list);
        map.put("moveup_dir_path","");
        map.put("current_dir_path","");
        try {
            /*
             * http://localhost:80/cmfz/upload/img
             * */
            String scheme = request.getScheme();            //http
            //PC-20190718ZLAM/192.168.1.156
            String host = InetAddress.getLocalHost().toString().split("/")[1];  //host
            int serverPort = request.getServerPort();       //port
            String contextPath = request.getContextPath();  //项目名
            String url = scheme + "://" + host + ":" + serverPort + contextPath + "/article/img/";
            map.put("current_url",url);
            map.put("total_count",imgs.length);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article queryArticleById(String id) {
        return articleDao.selectByPrimaryKey(id);
    }
}
