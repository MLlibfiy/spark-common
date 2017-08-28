package cn.ctyun.bigdata.forecast;

import cn.ctyun.bigdata.bdse.tour.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 98726 on 2017/8/24.
 * <p>
 * 数据集分为多个维度构建
 * 时间，休假，天气
 */
public class BuildTestDataSet {
    public static void main(String[] args) throws ParseException {
        //开始时间
        String beginDay = "20130101";
        //结束时间
        String endDay = "20131231";
        //当构建的时间
        String currentDay = beginDay;

        //周维度权重
        HashMap<Integer, Integer> week = new HashMap<>();
        week.put(1, 100);
        week.put(2, 110);
        week.put(3, 110);
        week.put(4, 120);
        week.put(5, 110);
        week.put(6, 140);
        week.put(0, 160);
        //月维度权重
        HashMap<Integer, Integer> month = new HashMap<>();
        month.put(1, 1000);
        month.put(2, 1200);
        month.put(3, 1250);
        month.put(4, 1000);
        month.put(5, 1300);
        month.put(6, 1350);
        month.put(7, 1250);
        month.put(8, 1400);
        month.put(9, 1100);
        month.put(10, 1100);
        month.put(11, 1350);
        month.put(12, 1400);
        //节假日权重
        HashMap<Integer, Integer> Leisure = new HashMap<>();
        Leisure.put(0, 0);
        Leisure.put(1, 1000);
        Leisure.put(2, 2000);
        Leisure.put(3, 3000);
        Leisure.put(4, 4000);
        Leisure.put(5, 5000);
        Leisure.put(6, 6000);
        Leisure.put(7, 7000);

        //天气维度
        HashMap<Integer, Integer> weather = new HashMap<>();
        weather.put(0,-500);
        weather.put(1,1000);

        //保存有天气信息
        HashMap<String, Integer> dayweather = new HashMap<>();

        //保存游客数据量
        HashMap<String, Integer> dayTour = new HashMap<>();

        while (Long.valueOf(currentDay) <= Long.valueOf(endDay)) {
            int dayWeekend = FestivalJson.getDayWeekend(currentDay);
            int dayMonth = FestivalJson.getDayMonth(currentDay);
            int dayVacationLength = FestivalJson.getDayVacationLength(currentDay);
            int i = new Random().nextInt(2);
            dayweather.put(currentDay,i);
            Integer weekWeight = week.get(dayWeekend);
            Integer monthWeight = month.get(dayMonth);
            Integer LeisureWeight = Leisure.get(dayVacationLength);
            Integer weatherWeight = weather.get(i);
            int footfall = weekWeight + monthWeight + LeisureWeight ;
            dayTour.put(currentDay,footfall);
            currentDay = DateUtils.getTomorrow(currentDay);

            String resultStr = footfall+"\t"
                    +"周"+dayWeekend+";"+1+";"
                    +dayMonth+"月"+":"+1+";"
                    +dayVacationLength+"天假"+":"+1;
                    //+"天气"+i+":"+1;
            System.out.println(resultStr);
        }

/*        for (Map.Entry<String, Integer> entry : dayTour.entrySet()) {
            System.out.println(entry.getKey()+"\t"+entry.getValue());
        }*/
    }
}
