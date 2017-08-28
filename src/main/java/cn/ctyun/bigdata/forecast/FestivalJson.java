package cn.ctyun.bigdata.forecast;


import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import cn.ctyun.bigdata.bdse.tour.util.DateUtils;
import com.google.gson.*;

/**
 * Created by 98726 on 2017/8/25.
 *
 * 获取日期对应节假日长度
 */
public  class FestivalJson{
    static ArrayList<String> festival;
    static HashMap<String,Integer> dayMap ;
    static {
        festival = new ArrayList<>();
        dayMap = new HashMap<>();
        JsonParser parse = new JsonParser();  //创建json解析器
        try {
            JsonObject json=(JsonObject) parse.parse(new FileReader(new File("D:\\project\\ieda-workspase\\ctyun\\spark-common\\src\\main\\java\\cn\\ctyun\\bigdata\\forecast\\节假日.json")));  //创建jsonObject对象

            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                String month  = entry.getKey();
                for (Map.Entry<String, JsonElement> stringJsonElementEntry : entry.getValue().getAsJsonObject().entrySet()) {
                    festival.add(month+stringJsonElementEntry.getKey());
                }
            }
            Collections.sort(festival);

            for (int i = 0; i < festival.size(); i++) {
                ArrayList<String> currDays = new ArrayList<>();
                Integer flag = 1;
                String currday = festival.get(i);
                currDays.add(currday);
                String nextDay = DateUtils.getTomorrow(currday);
                int j = i+1;
                for (; j < festival.size(); j++) {
                    String shDay = festival.get(j);
                    if(shDay.equals(nextDay)){
                        flag++;
                        i++;
                        nextDay = DateUtils.getTomorrow(nextDay);
                        currDays.add(shDay);
                    }else{
                        break;
                    }
                }
                for (String day : currDays) {
                    dayMap.put(day,flag);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static int getDayVacationLength(String day){
        if(dayMap.containsKey(day)){
            if(dayMap.get(day)>7){
                return 7;
            }
            return dayMap.get(day);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(FestivalJson.getDayMonth("20130107"));
    }


    static int getDayWeekend(String day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date parse = null;
        try {
            parse = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w;
    }

    static int getDayMonth(String day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date parse = null;
        try {
            parse = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        int w = cal.get(Calendar.MONTH)+1;
        return w;
    }
}
