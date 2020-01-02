package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    //分页查询全部
    Map<String,Object> queryAll(Integer page,Integer rows,String id);
    //增加章节
    void addChapter(Chapter chapter);
    //修改章节
    void changeChapter(Chapter chapter);
    //删除章节
    void removeChapter(String[] id,String albumId);
    //上传章节
    Map<String,String> chapterUpload(MultipartFile src, HttpSession session, String id);
    //下载章节
    String chapterDownload(String src, HttpServletRequest request, HttpServletResponse response) throws IOException;
    //查询章节
    List<Chapter> queryChapter(String id);
}
