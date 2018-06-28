package com.patrickzhong.spark.util;

import java.util.HashMap;

/**
 * Created by patrickzhong on 6/27/18.
 */
public class MapBuilder<T1, T2> {

    private HashMap<T1, T2> map = new HashMap<>();

    public MapBuilder<T1, T2> put(T1 k, T2 v){

        map.put(k, v);
        return this;

    }

    public HashMap<T1, T2> build(){
        return map;
    }

    public static MapBuilder<String, Object> get(){
        return new MapBuilder<>();
    }

}
