package com.baizhi.service;

import com.baizhi.entity.Article;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ArticleService {
    //分页展示
    Map<String,Object> queryAll(Integer page,Integer rows);
    //增加
    int addArticle(Article article);
    //修改
    int changeArticle(Article article);
    //删除
    int removeArticle(String[] ids);
    //上传图片
    Map<String,Object> uploadImg(MultipartFile img, HttpServletRequest request, HttpSession session);
    //获取图片
    Map<String,Object> getAllImgs(HttpSession session,HttpServletRequest request);
    //通过id查询
    Article queryArticleById(String id);
}
