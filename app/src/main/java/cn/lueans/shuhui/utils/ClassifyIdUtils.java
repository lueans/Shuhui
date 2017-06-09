package cn.lueans.shuhui.utils;

import java.util.HashMap;

/**
 * 类别Id帮助类
 * Created by 24277 on 2017/5/22.
 */

public class ClassifyIdUtils {
    public static final String comicTitle[] = {
      "全部",
      "同人",
      "鼠绘",
      "热血"
    };

    public static final int classifyId[] = {
            0,
            2,
            3,
            4
    };

    public static HashMap<String,Integer> map = new HashMap<>();

    public static HashMap<String,Integer> getInstance(){
        if (map == null || map.isEmpty()){
            synchronized (ClassifyIdUtils.class){
                if (map == null || map.isEmpty()) {
                    map = new HashMap<>();
                    setMap();
                }
            }
        }
        return map;
    }

    public static void setMap(){
        for (int i = 0; i < comicTitle.length; i++) {
            map.put(comicTitle[i],classifyId[i]);
        }
    }
}
