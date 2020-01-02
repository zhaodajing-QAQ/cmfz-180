package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //展示全部专辑
    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page,Integer rows){
        //调用业务
        Map<String, Object> map = albumService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("oper")
    @ResponseBody
    public Map<String,String> oper(String oper, Album album,String[] id){
        Map<String, String> map = new HashMap<String, String>();
        if ("add".equals(oper)){
            albumService.addAlbum(album);
            map.put("id",album.getId());
        }
        if ("edit".equals(oper)){
            if (album.getImg().equals("")){
                album.setImg(null);
                albumService.changeAlbum(album);
                map.put("id",null);
            }else{
                albumService.changeAlbum(album);
                map.put("id",album.getId());
            }
        }
        if ("del".equals(oper)){
            albumService.removeAlbum(id);
        }
        return map;
    }

    @RequestMapping("albumUpload")
    @ResponseBody
    public Map<String,String> albumUpload(MultipartFile img, HttpSession session,String id){
        Map<String, String> map = albumService.albumUpload(img, session, id);
        return map;
    }
}
