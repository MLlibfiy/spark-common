package cn.ctyun.bigdata.bdse.tour.util;

import scala.Tuple1;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 98726 on 2017/8/17.
 */
public class CityCoreBorderCluster {
    static HashMap<String, ArrayList<String>> map ;
    
    public static HashMap<String,  ArrayList<String>>  main(HashMap<String, ArrayList<String>> map2) {
        map = map2;
        HashMap<String, ArrayList<String>> hashmap = new HashMap<>();
       // while(map.isEmpty()){
        ArrayList<String> arr = new ArrayList<>();
        String o = map.keySet().toArray()[0].toString();
        hashmap.put("116470039870040",arr);
        run(o,arr);
        HashMap<String, Tuple2<String,String>> objectObjectHashMap = new HashMap<>();
        for (String s : arr) {
            String lon = s.substring(0, 7);
            String lat = s.substring(7, 15);
            Tuple2<String, String> doubleDoubleTuple2 = objectObjectHashMap.get(lon);
            if(doubleDoubleTuple2==null){
                doubleDoubleTuple2 = new Tuple2<>(lat,lat);
                objectObjectHashMap.put(lon,doubleDoubleTuple2);
            }else{
                String minlat = doubleDoubleTuple2._1();
                String maxlat = doubleDoubleTuple2._2() ;
                if(Double.valueOf(minlat) > Double.valueOf(lat)){
                    minlat = lat;
                }
                if(Double.valueOf(maxlat)<Double.valueOf(lat)){
                    maxlat =lat;
                }
                doubleDoubleTuple2 = new Tuple2<>(minlat,maxlat);
                objectObjectHashMap.put(lon,doubleDoubleTuple2);
            }
        }
        for (Map.Entry<String, Tuple2<String, String>> entry : objectObjectHashMap.entrySet()) {
            String key = entry.getKey();
            Tuple2<String, String> value = entry.getValue();
            System.out.print(key+value._1()+","+key+value._2()+",");
        }


        return hashmap;
    }

    private static void run(String str, ArrayList<String> arr){
        if(map.containsKey(str)){
            ArrayList<String> list = map.get(str);
            arr.add(str);
            map.remove(str);
            for (String s : list) {
                run(s,arr);
            }
        }
       
    }
    
}
