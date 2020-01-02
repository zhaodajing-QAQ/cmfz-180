package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    int deleteByPrimaryKey(String[] id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
    /*
    *   分页
    * */
    List<Article> selectAll(@Param("start")Integer start, @Param("rows")Integer rows);
    //查询数量
    int selectRecords();
}