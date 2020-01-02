package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    //查询
    List<Chapter> selectAll(@Param("start")Integer start, @Param("rows")Integer rows,@Param("id")String albumId);
    //增加
    void insertChapter(Chapter chapter);
    //修改
    void uploadChapter(Chapter chapter);
    //删除
    void deleteChapter(String[] id);
    //查询总条数
    int selectCount(String albumId);
    //查询章节
    List<Chapter> selectChapter(String id);
}
