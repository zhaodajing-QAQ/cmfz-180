package com.baizhi.controller;

import com.baizhi.entity.MapDto;
import org.omg.CosNaming.IstringHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/acharts")
public class AchartsController {
    @RequestMapping("/getData")
    public List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<MapDto> data = new ArrayList<>();
        data.add(new MapDto("北京", new Random().nextInt(1000)));
        data.add(new MapDto("上海", new Random().nextInt(1000)));
        data.add(new MapDto("广州", new Random().nextInt(1000)));
        data.add(new MapDto("深圳", new Random().nextInt(1000)));
        data.add(new MapDto("杭州", new Random().nextInt(1000)));
        data.add(new MapDto("湖南", new Random().nextInt(1000)));
        data.add(new MapDto("湖北", new Random().nextInt(1000)));
        data.add(new MapDto("山西", new Random().nextInt(1000)));
        data.add(new MapDto("山东", new Random().nextInt(1000)));

        for (MapDto mapDto : data) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",mapDto.getName());
            map.put("value",mapDto.getValue());
            list.add(map);
        }
        return list;
    }
}