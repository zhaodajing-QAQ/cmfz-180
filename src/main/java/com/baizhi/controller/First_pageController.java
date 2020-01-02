package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
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
@RequestMapping("/first_page")
public class First_pageController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/main")
    public Map<String, List<Map<String,Object>>> showFirst_page(String type, String sub_type, HttpServletRequest request){
        try {
            HashMap<String, List<Map<String, Object>>> map = new HashMap<>();
            //PC-20190718ZLAM/192.168.1.156
            //获取全路径
            String scheme = request.getScheme();            //http
            String host = InetAddress.getLocalHost().toString().split("/")[1];  //host
            int serverPort = request.getServerPort();       //port
            String contextPath = request.getContextPath();  //项目名
            String bannerUrl = scheme + "://" + host + ":" + serverPort + contextPath + "/upload/";
            String albumUrl = scheme + "://" + host + ":" + serverPort + contextPath + "/album/img/";
            //查询轮播图信息
            List<Banner> banners = bannerService.queryAll();
            ArrayList<Map<String, Object>> header = new ArrayList<>();
            for (Banner banner : banners) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("thumbnail",bannerUrl+banner.getImg());
                map1.put("desc",banner.getTitle());
                map1.put("id",banner.getId());
                header.add(map1);
            }
            map.put("header",header);

            //查询专辑信息
            List<Album> albums = albumService.queryAll();
            List<Map<String, Object>> body = new ArrayList<>();
            for (Album album : albums) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("thumbnail",albumUrl+album.getImg());
                map1.put("title",album.getTitle());
                map1.put("author",album.getAuthor());
                map1.put("type",0);
                map1.put("set_count",album.getCount());
                map1.put("create_date",album.getCreate_Date());
                body.add(map1);
            }
            map.put("body",body);
            return map;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
