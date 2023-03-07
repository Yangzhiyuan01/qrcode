package com.wechat.qrcode.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    /**
     *
     * @param i 第几天
     * @param b true:查询未来日期,  false:查询过去日期
     * @return
     */
    public static String getDays(int i, boolean b) {
        Calendar calendar = Calendar.getInstance();
        if (b) {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + i);
        }else {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
        }
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS);
        /**带星期的*/
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd (EEEE)");
        String result = format.format(today);
        return result;
    }

    /***
     *  查询前后n天日期
     *
     * @param args
     */
    public static void main(String[] args) {
        int intervals=7;
        List passDaysList = new ArrayList<>();
        List futureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            passDaysList.add(getDays(i,false));
            futureDaysList.add(getDays(i,true));
        }
        System.out.println("过去日期: "+passDaysList) ;
        System.out.println("==============================================================================================");
        System.out.println("未来日期: "+futureDaysList) ;
    }
}
