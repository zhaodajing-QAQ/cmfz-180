package com.baizhi.service;

import com.baizhi.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页查询
    public Map<String,Object> queryAll(Integer page,Integer rows);
    //增加专辑
    public void addAlbum(Album album);
    //修改专辑
    public void changeAlbum(Album album);
    //批量删除专辑
    public void removeAlbum(String[] id);
    //上传专辑
    public Map<String,String> albumUpload(MultipartFile img, HttpSession session,String id);
    //查询全部
    public List<Album> queryAll();
    //通过ID查询
    public Album queryAlbumById(String id);
}
