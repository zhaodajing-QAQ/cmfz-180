package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WenController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/wen")
    public Map<String,Object> getWen(String id, String uid, HttpServletRequest request){
        try {
            Map<String, Object> map = new HashMap<>();
            //查询数据
            Album album = albumService.queryAlbumById(id);
            //存放数据
            Map<String, Object> introduction = new HashMap<>();
            String scheme = request.getScheme();            //http
            String host = InetAddress.getLocalHost().toString().split("/")[1];
            int serverPort = request.getServerPort();       //port
            String contextPath = request.getContextPath();  //项目名
            String albumUrl = scheme + "://" + host + ":" + serverPort + contextPath + "/album/img/"; //图片url
            String chapterUrl = scheme + "://" + host + ":" + serverPort + contextPath + "/album/chapter/"; //章节url
            introduction.put("thumbnail",albumUrl+album.getImg());
            introduction.put("title",album.getTitle());
            introduction.put("score",album.getScore());
            introduction.put("author",album.getAuthor());
            introduction.put("broadcast",album.getBroadcaster());
            introduction.put("set_count",album.getCount());
            introduction.put("brief",album.getBrief());
            introduction.put("create_date",album.getCreate_Date());
            map.put("introduction",introduction);
            //查询专辑下列表
            List<Chapter> chapters = chapterService.queryChapter(id);
            ArrayList<Map<String,Object>> list = new ArrayList<>();
            for (Chapter chapter : chapters) {
                Map<String, Object> chap = new HashMap<>();
                chap.put("title",chapter.getTitle());
                chap.put("download_url",chapterUrl+chapter.getSrc());
                chap.put("size",chapter.getSize());
                chap.put("duration",chapter.getDuration());
                list.add(chap);
            }
            map.put("list",list);
            return map;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
