package com.zzx.bean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cache {
    public static Map<Integer, List<String>> cacheList;

    public static List<LogEntity> list=new CopyOnWriteArrayList<>();
}
