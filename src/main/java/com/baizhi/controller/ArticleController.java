package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page,Integer rows){
        //调用业务
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public String edit(String oper,Article article,String[] id){
        if ("del".equals(oper)){
            int i = articleService.removeArticle(id);
            if (i==1){
                return "修改成功";
            }else{
                return "修改失败";
            }
        }
        return null;
    }

    /*
        图片上传
    */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String,Object> uploadImg(MultipartFile img, HttpServletRequest request, HttpSession session){
        Map<String, Object> map = articleService.uploadImg(img, request, session);
        return map;
    }

    /*
    *   获取图片
    * */
    @ResponseBody
    @RequestMapping("/getAllImgs")
    public Map<String,Object> getAllImgs(HttpServletRequest request,HttpSession session){
        Map<String, Object> map = articleService.getAllImgs(session,request);
        return map;
    }

    @ResponseBody
    @RequestMapping("/addArticle")
    public String addArticle(Article article){
        if(article.getTitle()==""|article.getAuthor()==""|article.getContent()==""){
            return "请输入内容";
        }else{
            int i = articleService.addArticle(article);
            if (i==1){
                return "添加成功";
            }else{
                return "添加失败";
            }
        }
    }

    @ResponseBody
    @RequestMapping("/editArticle")
    public String editArticle(Article article){
        if(article.getTitle()==null|article.getAuthor()==null|article.getContent()==null){
            return "请输入内容";
        }else {
            int i = articleService.changeArticle(article);
            if (i == 1) {
                return "修改成功";
            } else {
                return "修改失败";
            }
        }
    }
}