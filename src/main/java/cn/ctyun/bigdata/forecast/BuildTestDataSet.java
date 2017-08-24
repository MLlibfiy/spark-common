package cn.ctyun.bigdata.forecast;

import java.util.HashMap;

/**
 * Created by 98726 on 2017/8/24.
 *
 * 数据集分为多个维度构建
 * 时间，休假，天气
 */
public class BuildTestDataSet {
    public static void main(String[] args) {
        //开始时间
        String beginDay = "20170101";
        //结束时间
        String endDay = "20180101";
        //当构建的时间
        String currentDay = beginDay;

        //周维度权重
        HashMap<Integer, Integer> week = new HashMap<>();
        week.put(1,100); week.put(2,110);week.put(3,110);week.put(4,120); week.put(5,110); week.put(6,140); week.put(7,160);
        //月维度权重
        HashMap<Integer, Integer> month = new HashMap<>();
        month.put(1,1000);month.put(2,1200);month.put(3,1250);month.put(4,1000);month.put(5,1300);month.put(6,1350);
        month.put(7,1250);month.put(8,1400);month.put(9,1100);month.put(10,1100);month.put(11,1350);month.put(12,1400);
        //节假日权重
        HashMap<Integer, Integer> Leisure = new HashMap<>();
        Leisure.put(1,1000);Leisure.put(2,2000);Leisure.put(3,3000);Leisure.put(4,4000);Leisure.put(5,5000);

        //天气维度
        HashMap<Integer, Integer> weather = new HashMap<>();
        while (Long.valueOf(currentDay)<Long.valueOf(endDay)){

        }
    }
}
