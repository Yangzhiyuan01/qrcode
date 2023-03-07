package com.wechat.qrcode.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static final ZoneId ZONEID = ZoneId.systemDefault();
    public static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss S";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyymmddhhmmssSSS";
    public static final String YYYYMMDD_T_HHMMSS = "yyyyMMdd'T'HHmmss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMM = "yyyyMM";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYY_MM = "yyyy-MM";


    public DateUtil() {
    }

    public static Date stringToDate(String dateStr, String pattern) {
        int index = dateStr.lastIndexOf(".");
        if (index >= 0) {
            dateStr = dateStr.substring(0, index);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDate ldt = LocalDate.parse(dateStr, dtf);
        ZonedDateTime zdt = ldt.atStartOfDay().atZone(ZONEID);
        return Date.from(zdt.toInstant());
    }

    public static String addYearMonth(String dateStr, String pattern, long monthsToAdd) {
        YearMonth yearMonth = YearMonth.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
        return yearMonth.plusMonths(monthsToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date stringToTime(String dateStr, String pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.parse(dateStr, dtf);
        ZonedDateTime zdt = ldt.atZone(ZONEID);
        return Date.from(zdt.toInstant());
    }


    public static int getDiffDaysOfTwoDate(String paramDate1, String paramDate2) {
        return Math.abs(getDiffDays(paramDate1, paramDate2));
    }

    public static int getDiffDaysOfTwoDate(Date date1, Date date2) {
        return Math.abs(getDiffDays(date1, date2));
    }

    public static int getDiffDaysOfTwoDateByNegative(String paramDate1, String paramDate2) {
        return getDiffDays(paramDate1, paramDate2);
    }

    public static int getDiffDays(String paramDate1, String paramDate2) {
        Date date1 = stringToDate(paramDate1, "yyyy-MM-dd");
        Date date2 = stringToDate(paramDate2, "yyyy-MM-dd");
        return getDiffDays(date1, date2);
    }

    public static int getDiffDays(Date date1, Date date2) {
        Long diffTimes = date1.getTime() - date2.getTime();
        Long diffDays = diffTimes / 86400000L;
        return diffDays.intValue();
    }

    public static String getDayOfWeek(Date paramDate, int retFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        int dayOfWeek = c.get(7) == 1 ? 7 : c.get(7) - 1;
        String dayOfWeekStr = null;
        switch (dayOfWeek) {
            case 1:
                dayOfWeekStr = 0 == retFormat ? "1" : "一";
                break;
            case 2:
                dayOfWeekStr = 0 == retFormat ? "2" : "二";
                break;
            case 3:
                dayOfWeekStr = 0 == retFormat ? "3" : "三";
                break;
            case 4:
                dayOfWeekStr = 0 == retFormat ? "4" : "四";
                break;
            case 5:
                dayOfWeekStr = 0 == retFormat ? "5" : "五";
                break;
            case 6:
                dayOfWeekStr = 0 == retFormat ? "6" : "六";
                break;
            case 7:
                dayOfWeekStr = 0 == retFormat ? "7" : "日";
        }

        return dayOfWeekStr;
    }

    public static String dateDiff(Long startTime, Long endTime) {
        long nd = 86400000L;
        long nh = 3600000L;
        long nm = 60000L;
        long ns = 1000L;
        long diff = endTime - startTime;
        long day = Math.abs(diff / nd);
        long hour = Math.abs(diff % nd / nh);
        long min = Math.abs(diff % nd % nh / nm);
        long sec = Math.abs(diff % nd % nh % nm / ns);
        long mill = Math.abs(diff % ns);
        return "" + day + "天" + hour + "时" + min + "分" + sec + "秒" + mill + "毫秒";
    }

    public static void main(String[] args) throws InterruptedException {
        Long dateTime1 = System.currentTimeMillis();
        Thread.sleep(1000L);
        Long dateTime2 = System.currentTimeMillis();
        System.out.println(dateDiff(dateTime1, dateTime2));
        System.out.println(dateTime1);
        System.out.println(dateTime2);
        System.out.println(stringToTime("2019-03-15 01:01:12", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(stringToDate("2019-03-15", "yyyy-MM-dd"));
    }
}
