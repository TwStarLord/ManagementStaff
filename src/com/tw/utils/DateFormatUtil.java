package com.tw.utils;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    /**
     * 根据给定的字符串时间获取月份，用来比较出差/请假的开始与结束时间是否在同一月
     * @param date
     * @return
     */
    public static int getMonthByStringDate(String date){
        return Integer.parseInt(date.split("-")[1]); //月
    }

    /**
     * 根据月份获取该月的第一天
     * @param starttime
     * @return
     */
    public static String getFirstDayOfMonth(String starttime) {
        int year = Integer.parseInt(starttime.split("-")[0]);  //年
        int month = Integer.parseInt(starttime.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 根据月份获取该月的最后一天
     * @param starttime 如果开始时间和结束时间不在一个月的话，就获取开始时间所在月份的最后一天
     * @return
     */
    public static String getLastDayOfMonth(String starttime){
        int year = Integer.parseInt(starttime.split("-")[0]);  //年
        int month = Integer.parseInt(starttime.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month); //设置当前月的上一个月
        // 获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
    }


    /**
     * 获取月份
     * @return
     */
    public static Integer getMonth(){
        Calendar c1 = Calendar.getInstance();
        int month = c1.get(Calendar.MONTH) + 1; //这里默认月份是从0开始
        return month;
    }

    /**
     * 获取打卡时间
     * @return
     */
    public static String getCurrentTime(){
        Date recordtime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(recordtime);
        return dateString;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        Date recordtime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(recordtime);
        return dateString;
    }
    /**
     * 在确认出差或者请假申请通过之后，通过比较出差和请假的开始时间，如果和系统的当前时间相同的话，就将该员工的
     * 状态修改为出差/请假，如果不是，则不进行修改，后期交由quartz来执行状态修改操作。
     * 开始日期如果时间相等，即请假或者出差日期即今天，返回0
     * 开始日期在系统日期之后，则返回1，后期交由quartz来修改员工的状态
     * @param starttime
     * @return
     */
    public static int dateCompare(String starttime){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance(); //请假或者出差的开始日期
        Calendar c2 = Calendar.getInstance(); //系统当前日期
        try {
            c1.setTime(formatter.parse(starttime));
            c2.setTime(formatter.parse(formatter.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c1.compareTo(c2);
    }


    /**
     * 比较datetime时间大小
     * @return
     */
    public static int timeCompare(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String recordtime = formatter.format(new Date());
        String recordtimeOfStandardStr = "8:00:00";
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        int year = c1.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH) + 1; //这里默认月份是从0开始
        int day = c1.get(Calendar.DAY_OF_MONTH);
        recordtimeOfStandardStr = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day) + " " +recordtimeOfStandardStr;
//		System.out.println(recordtimeOfStandardStr);
        try {
            c1.setTime(formatter.parse(recordtime));  //c1是打卡时间
            c2.setTime(formatter.parse(recordtimeOfStandardStr));  //c2是标准时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //打卡时间比标准时间之前则为-1  之后则为1
        return c1.compareTo(c2);
    }

    /**
     * 用来计算两个日期之间的天数，计算请假天数，将数据存储到数据库之后再根据角色权限来判断是否拥有审批权限
     * @param starttimeString
     * @param endtimeString
     * @return
     */
    public static long getBetweenDaysFromTwoDates(String starttimeString,String endtimeString) {

        Date starttime = null;
        Date endtime = null;
        try{
            starttime = new SimpleDateFormat("yyyy-MM-dd").parse(starttimeString);
            endtime = new SimpleDateFormat("yyyy-MM-dd").parse(endtimeString);
        }catch (ParseException e){
                throw new RuntimeException("日期转换出错");
        }
        long betweendays = (endtime.getTime() - starttime.getTime()) / (1000L*60*60*24);
        return betweendays;
    }

    /*public static void main(String[] args) {
//        long betweenDaysFromTwoDates = getBetweenDaysFromTwoDates("2020-5-1", "2020-5-11");
        String test = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        System.out.println(test);*/

}
