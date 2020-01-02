package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //查询
    List<Album> selectAll(@Param("start")Integer start, @Param("rows")Integer rows);
    //增加
    void insertAlbum(Album album);
    //修改
    void updateAlbum(Album album);
    //删除
    void deleteAlbum(String[] id);
    //查询总条数
    int selectCount();
    //通过id查询
    Album selectById(String id);
    //查询全部
    List<Album> selectAllAlbum();
}
