package com.zft.pojo;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by eltntawy on 17/04/15.
 */
public class ServiceType {
    public static final String TYPE_FOOD="food";
    private static Map<Integer,String> map = new TreeMap<Integer,String>();


    public static Map<Integer,String> getServiceType(){
        map.put(1, TYPE_FOOD);
        return map ;
    }

    public static String getServiceType(int key){
        return map.get(key);
    }
}
