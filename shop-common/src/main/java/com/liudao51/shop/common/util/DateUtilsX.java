package com.liudao51.shop.common.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * class: 日期工具类（pattern全称为"yyyy-MM-dd HH:mm:ss.SSS"）
 * <p>
 * Created by jewel on 2019/4/19.
 */
public class DateUtilsX {
    /**
     * function: 校验日期是否合法
     *
     * @param dateStr
     * @return
     */
    public static boolean isValid(String dateStr, String pattern) {
        try {
            (new SimpleDateFormat(pattern)).parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * function: 转换为日期对象
     *
     * @param dateStr String
     * @param pattern String "yyyy-MM-dd HH:mm:ss.SSS"
     * @return Date
     * @throws Exception
     */
    public static Date valueOf(String dateStr, String pattern) throws Exception {
        return (new SimpleDateFormat(pattern)).parse(dateStr);
    }

    /**
     * function: 格式化日期对象
     *
     * @param date    Date
     * @param pattern String "yyyy-MM-dd HH:mm:ss.SSS"
     * @return String
     * @throws Exception
     */
    public static String toString(Date date, String pattern) throws Exception {
        return (new SimpleDateFormat(pattern)).format(date);
    }

    /**
     * function: 获取当前日期
     *
     * @return Date
     */
    public static Date getDate() throws Exception {
        return new Date();
    }

    /**
     * 返回日期中的某值(支持单独取月/日/时/分/秒）
     *
     * @param date      Date
     * @param valueType int 可取值为Calendar的枚举值(Calendar.YEAR,Calendar.MONTH,Calendar.DATE...)
     * @return int -1:为没有对应的类型
     * @throws Exception
     */
    public static int getDateValue(Date date, int valueType) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int value = -1;

        //e.g. 2019-02-03 10:04:15.250
        switch (valueType) {
            //日期的yyyy值,2019
            case Calendar.YEAR:
                value = cal.get(Calendar.YEAR);
                break;

            //日期的MM值,02
            case Calendar.MONTH:
                value = cal.get(Calendar.MONTH) + 1;
                break;

            //日期的dd值,03
            case Calendar.DATE:
                value = cal.get(Calendar.DATE);
                break;

            //日期的HH值,10
            case Calendar.HOUR:
                value = cal.get(Calendar.HOUR);
                break;

            //日期的mm值,04
            case Calendar.MINUTE:
                value = cal.get(Calendar.MINUTE);
                break;

            //日期的ss值,15
            case Calendar.SECOND:
                value = cal.get(Calendar.SECOND);
                break;

            //日期的SSS值,250
            case Calendar.MILLISECOND:
                value = cal.get(Calendar.MILLISECOND);
                break;

            //一年中的第几周
            case Calendar.WEEK_OF_YEAR:
                value = cal.get(Calendar.WEEK_OF_YEAR);
                break;

            //一月中的第几周
            case Calendar.WEEK_OF_MONTH:
                value = cal.get(Calendar.WEEK_OF_MONTH);
                break;

            //一年的第几天
            case Calendar.DAY_OF_YEAR:
                value = cal.get(Calendar.DAY_OF_YEAR);
                break;

            //一周的第几天（星期日为一周的第一天输出为1, 星期一输出为2, 以此类推...）
            case Calendar.DAY_OF_WEEK:
                value = cal.get(Calendar.DAY_OF_WEEK);
                break;
        }

        return value;
    }

    /**
     * function: 根据日期计算当周的第一天与最后一天
     *
     * @param date Date
     * @return Map
     */
    public static Map<String, Object> getWeekFirstAndLast(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());

        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first", imptimeBegin);
        map.put("last", imptimeEnd);

        return map;
    }

    /**
     * function: 根据日期计算当月的第一天与最后一天
     *
     * @param date Date
     * @return Map
     */
    public static Map<String, Object> getMonthFirstAndLast(Date date) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        // 本月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());

        // 本月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first", day_first);
        map.put("last", day_last);

        return map;

    }

    /**
     * function: 根据日期计算当年的第一天与最后一天
     *
     * @param date Date
     * @return Map
     */
    public static Map<String, Object> getYearFirstAndLast(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int year = date.getYear() + 1900;
        calendar.clear();
        calendar.set(Calendar.YEAR, year);

        //当年的第一天
        Date currYearFirst = calendar.getTime();
        calendar.set(Calendar.YEAR, year + 1);

        //当年的最后一天
        calendar.add(Calendar.DATE, -1);
        Date lastYearFirst = calendar.getTime();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("first", sdf.format(currYearFirst));
        map.put("last", sdf.format(lastYearFirst));

        return map;
    }

    /**
     * function: 获取指定日期的"年/月/日" 加(减)后的日期
     *
     * @param date     Date
     * @param addYear  int
     * @param addMonth int
     * @param addDate  int
     * @return Date
     * @throws Exception
     */
    public static Date dateAdd(Date date, int addYear, int addMonth, int addDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, addYear);
        calendar.add(Calendar.MONTH, addMonth);
        calendar.add(Calendar.DATE, addDate);

        return calendar.getTime();
    }

    /**
     * function：时间相减得到天数
     *
     * @param startDate Date
     * @param endDate   Date
     * @return long
     * @throws Exception
     */
    public static long dateDif(Date startDate, Date endDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * function：时间相减得到秒数
     *
     * @param startDate
     * @param endDate
     * @param pattern
     * @return
     * @throws Exception
     */
    public static long timestampDif(Date startDate, Date endDate, String pattern) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return (endDate.getTime() - startDate.getTime()) / 1000;
    }
}
