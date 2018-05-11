package com.example.wzbc.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WZBC on 2018/4/11.
 */

public class getDate {
    public static List<Map<String, Object>> getMyData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("title", "G1");
        map.put("info", "google 1");
        map.put("date", "2015/05/10");
        list.add(map);

        map = new HashMap<>();
        map.put("title", "G2");
        map.put("info", "google 2");
        map.put("date", "2016/05/10");
        list.add(map);

        map = new HashMap<>();
        map.put("title", "G3");
        map.put("info", "google 3");
        map.put("date", "2017/05/10");
        list.add(map);

        return list;
    }
}
