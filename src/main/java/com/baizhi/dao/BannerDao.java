package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //分页查询全部
    List<Banner> selectAll(@Param("start")Integer start,@Param("rows")Integer rows);
    //添加
    void bannerAdd(Banner banner);
    //修改
    void bannerUpload(Banner banner);
    //批量删除
    void bannerDelete(String[] ids);
    //查询总条数
    int selectCount();
    //查询全部
    public List<Banner> selectAllBanner();
}
