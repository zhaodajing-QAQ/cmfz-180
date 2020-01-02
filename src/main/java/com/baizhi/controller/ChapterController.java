package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //展示全部
    @RequestMapping("showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page,Integer rows,String id){
        //调用业务
        Map<String, Object> map = chapterService.queryAll(page, rows,id);
        return map;
    }

    @RequestMapping("oper")
    @ResponseBody
    public Map<String,String> oper(String oper, Chapter chapter, String[] id) {
        Map<String, String> map = new HashMap<String, String>();
        if ("add".equals(oper)) {
            chapterService.addChapter(chapter);
            map.put("id", chapter.getId());
        }
        if ("edit".equals(oper)) {
            if (chapter.getSrc().equals("")) {
                chapter.setSrc(null);
                chapterService.changeChapter(chapter);
                map.put("id", null);
            } else {
                chapterService.changeChapter(chapter);
                map.put("id", chapter.getId());
            }
        }
        if ("del".equals(oper)) {
            chapterService.removeChapter(id,chapter.getAlbum_Id());
        }
        return map;
    }

    //上传章节
    @RequestMapping("/chapterUpload")
    @ResponseBody
    public Map<String,String> chapterUpload(MultipartFile src, HttpSession session, String id){
        Map<String, String> map = chapterService.chapterUpload(src, session, id);
        return map;
    }

    //下载章节
    @RequestMapping("/chapterDownload")
    @ResponseBody
    public String chapterDownload(String src, HttpServletRequest request, HttpServletResponse response){
        String msg = "";
        try {
            msg = chapterService.chapterDownload(src, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
