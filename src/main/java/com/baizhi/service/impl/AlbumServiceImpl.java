package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    /*
    *   分页展示全部
    * */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        /**   计算起始
         *   page: 当前页
         *   rows: 数据
         *   total: 总页数
         *   records: 总条数
         **/
        //开始条数
        Integer start = (page-1)*rows;
        //数据
        List<Album> list = albumDao.selectAll(start, rows);
        //总条数
        int records = albumDao.selectCount();
        //总页数
        int total = records % rows == 0 ? records / rows : records / rows + 1;

        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",total);
        map.put("records",records);
        return map;
    }

    /*
    *   添加专辑
    * */
    @Override
    @Transactional
    public void addAlbum(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreate_Date(new Date());
        album.setCount("0");
        albumDao.insertAlbum(album);
    }

    /*
    *   修改专辑
    * */
    @Override
    @Transactional
    public void changeAlbum(Album album) {
        albumDao.updateAlbum(album);
    }

    /*
    *   批量删除专辑
    * */
    @Override
    @Transactional
    public void removeAlbum(String[] id) {
        albumDao.deleteAlbum(id);
    }

    /*
    *   上传专辑
    * */
    @Override
    @Transactional
    public Map<String,String> albumUpload(MultipartFile img, HttpSession session, String id) {
        Map<String, String> map = new HashMap<String,String>();
        //1.获取upload的路径
        String realPath = session.getServletContext().getRealPath("/album/img");
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
        Album album = new Album();
        album.setId(id);
        album.setImg(filename);
        albumDao.updateAlbum(album);
        map.put("msg","上传成功");
        return map;
    }

    /*
    *   查询全部
    * */
    @Override
    public List<Album> queryAll() {
        return albumDao.selectAllAlbum();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album queryAlbumById(String id) {
        return albumDao.selectById(id);
    }
}
