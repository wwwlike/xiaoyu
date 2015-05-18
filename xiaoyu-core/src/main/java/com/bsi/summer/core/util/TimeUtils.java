package com.bsi.summer.core.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author wwwlike
 */
public class TimeUtils {

    public static Timestamp getCurrentSqlTime() {
        return new Timestamp(new Date().getTime());
    }
    
    public static java.sql.Date getCurrentSqlDate() {
        return new java.sql.Date(new Date().getTime());
    }
    
    public static Timestamp getOneHourAfterSqlTime() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.HOUR,1);
        return new Timestamp(gc.getTimeInMillis());
    }
    
    //传入月返回季度
    public static int getSeason(int month){
        int season = 0;
        if(month%3 ==0)
            season = month/3;
        else
            season = (int) (month/3.0) + 1;
        return season;
    }
    
    public static String getTime() {
		String datestr =  "" ;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			datestr = df.format(new java.util.Date()) ;
		}
		catch (Exception ex) {
		}
		return datestr ;
    }

	public static String getTime(String text) {
		String datestr =  "" ;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(text);
			datestr = df.format(new java.util.Date()) ;
		}
		catch (Exception ex) {
		}
		return datestr ;
    }
	
	public static String getDate() {
        String datestr =  "" ;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			datestr = df.format(new java.util.Date()) ;
		}
		catch (Exception ex) {
		}
		return datestr;
    }
	
	// 得到几天后的日期,输入负数即为几天前
	public static String getDateAfter(String date,int day) {
		String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        if(date!=null && !"".equals(date.trim()))
        	gc.setTime( TimeUtils.stringToDate(date, "yyyy-MM-dd") );
        else
        	gc.setTime(new Date());
        gc.add(GregorianCalendar.DATE,day);
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
	
	
	// 得到几天后的日期,输入负数即为几天前
	public static Date getDateAfter(Date date,int day) {
		String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(GregorianCalendar.DATE,day);
        return gc.getTime();
    }
	
	public static int subDateByDay(Date start,Date end) {
	    int days = 0;
	    days = (int) ((end.getTime()-start.getTime()) / (1000*60*60*24));
	    return days;
	}
	
	public static int subDateByMin(Date start,Date end) {
	    int minute = 0;
	    minute = (int) ((end.getTime()-start.getTime()) / (1000*60));
	    return minute;
	}
	
	//将String转化为Date
	public static Date stringToDate(String s,String format) {
	    Date temp = null;
	    try{
	        temp = new SimpleDateFormat(format).parse(s);
	    }catch(Exception e){
	        return null;
	    }
	    return temp;
	}
	
	//将date转化为String
	public static String dateToString(Date date,String format){
		String temp = null;
		try{
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			temp = df.format(date) ;
		}catch(Exception e){
			//ignore
		}
		return temp;
	}
	
	//比较两个时间
	public static boolean isAfter(String a,String b) {
	    boolean isAfter = false;
	    Date A = stringToDate(a,"yyyy-MM-dd HH:mm:ss");
	    Date B = stringToDate(b,"yyyy-MM-dd HH:mm:ss");
	    if(A!=null && B!=null && A.after(B))
	        isAfter = true;
	    return isAfter;
	}
	
	
	
	
	//验证时间是否在某个区间
	public static boolean isBetween(Date date,Date start,Date end) {
	    boolean isBetween = false;
	    if(date!=null && start!=null && end!=null
	            && date.after(start) && !date.after(end))
	        isBetween = true;
	    return isBetween;
	}
    
    //寻找当天的，一个月前的日期
    public static String getLastMonthDay() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.MONTH,-1);
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    //寻找当前的，一年后的日期
    public static String getNextYearDay() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.YEAR,1);
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getBeginOfWeek() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 1) {
                break;
            }
            gc.add(GregorianCalendar.DATE,-1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getEndOfWeek() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 0) {
                break;
            }
            gc.add(GregorianCalendar.DATE,1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getBeginOfMonth() {
        return TimeUtils.getTime("yyyy-MM")+"-01";
    }
    
    public static String getEndOfMonth() {
        String temp = TimeUtils.getTime("yyyy-MM")+"-01";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( TimeUtils.stringToDate(temp,"yyyy-MM-dd") );
        gc.add(GregorianCalendar.MONTH,1);
        gc.add(GregorianCalendar.DATE,-1);
        temp = TimeUtils.dateToString( gc.getTime(),"yyyy-MM-dd" );
        return temp;
    }
    
    public static String getBeginOfSeason() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        int season = getSeason( gc.get(GregorianCalendar.MONTH)+1 );
        int first_month_of_season = season*3-2;
        if(first_month_of_season >9)
            temp = gc.get(GregorianCalendar.YEAR)+"-"+first_month_of_season+"-01";
        else
            temp = gc.get(GregorianCalendar.YEAR)+"-0"+first_month_of_season+"-01";
        return temp;
    }
    
    public static String getEndOfSeason() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        int season = getSeason( gc.get(GregorianCalendar.MONTH)+1 );
        int end_month_of_season = season*3+1;
        if(end_month_of_season >9)
            temp = gc.get(GregorianCalendar.YEAR)+"-"+end_month_of_season+"-01";
        else
            temp = gc.get(GregorianCalendar.YEAR)+"-0"+end_month_of_season+"-01";
        gc.setTime( TimeUtils.stringToDate(temp,"yyyy-MM-dd") );
        gc.add(GregorianCalendar.DATE,-1);
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getBeginOfLastWeek() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.DATE,-7);
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 1) {
                break;
            }
            gc.add(GregorianCalendar.DATE,-1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getEndOfLastWeek() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.DATE,-7);
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 0) {
                break;
            }
            gc.add(GregorianCalendar.DATE,1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getBeginOfLastMonth() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.MONTH,-1);
        String temp = TimeUtils.dateToString(gc.getTime(),"yyyy-MM")+"-01";
        return temp;
    }
    
    public static String getEndOfLastMonth() {
        String temp = TimeUtils.getTime("yyyy-MM")+"-01";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( TimeUtils.stringToDate(temp,"yyyy-MM-dd") );
        gc.add(GregorianCalendar.DATE,-1);
        temp = TimeUtils.dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public static String getBeginOfLastSeason() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.MONTH,-3);
        int season = getSeason( gc.get(GregorianCalendar.MONTH)+1 );
        int first_month_of_season = season*3-2;
        if(first_month_of_season >9)
            temp = gc.get(GregorianCalendar.YEAR)+"-"+first_month_of_season+"-01";
        else
            temp = gc.get(GregorianCalendar.YEAR)+"-0"+first_month_of_season+"-01";
        return temp;
    }
    
    public static String getEndOfLastSeason() {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( new Date() );
        gc.add(GregorianCalendar.MONTH,-3);
        int season = getSeason( gc.get(GregorianCalendar.MONTH)+1 );
        int end_month_of_season = season*3+1;
        if(end_month_of_season >9)
            temp = gc.get(GregorianCalendar.YEAR)+"-"+end_month_of_season+"-01";
        else
            temp = gc.get(GregorianCalendar.YEAR)+"-0"+end_month_of_season+"-01";
        gc.setTime( TimeUtils.stringToDate(temp,"yyyy-MM-dd") );
        gc.add(GregorianCalendar.DATE,-1);
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    public ArrayList splitTime(String type,String start,String end){
        ArrayList list = new ArrayList();
        if("month".equals(type)) {
            Date startMonth = TimeUtils.stringToDate(start,"yyyy-MM");
            Date endMonth = TimeUtils.stringToDate(end,"yyyy-MM");
            GregorianCalendar startGC = new GregorianCalendar();
            startGC.setTime(startMonth);
            GregorianCalendar endGC = new GregorianCalendar();
            endGC.setTime(endMonth);
            while(startGC.before(endGC)){
                list.add( dateToString(startGC.getTime(),"yyyy-MM") );
                startGC.add(GregorianCalendar.MONTH,1);
            }
            list.add(end);
        }
        else if("date".equals(type)){
            Date startDate = TimeUtils.stringToDate(start,"yyyy-MM-dd");
            Date endDate = TimeUtils.stringToDate(end,"yyyy-MM-dd");
            GregorianCalendar startGC = new GregorianCalendar();
            startGC.setTime(startDate);
            GregorianCalendar endGC = new GregorianCalendar();
            endGC.setTime(endDate);
            while(startGC.before(endGC)){
                list.add( dateToString(startGC.getTime(),"yyyy-MM-dd") );
                startGC.add(GregorianCalendar.DATE,1);
            }
            list.add(end);
        }
        else if("season".equals(type)){
            int startYear = Integer.parseInt(start.substring(0,4));
            int startSeason = Integer.parseInt(start.substring(5));
            int endYear = Integer.parseInt(end.substring(0,4));
            int endSeason = Integer.parseInt(end.substring(5));
            
            while(startYear<=endYear){
                if(startYear<endYear){
                    while(startSeason<=4)
                        list.add( startYear+"-"+startSeason++);
                    startSeason = 1;
                }else{
                    while(startSeason<=endSeason)
                        list.add( startYear+"-"+startSeason++);
                }
                startYear++;
            }
        }
        else if("year".equals(type)){
            int startYear = Integer.parseInt(start);
            int endYear = Integer.parseInt(end);
            while(startYear<=endYear){
                list.add(Integer.toString(startYear++));
            }
        }
        return list;
    }
    
    /**
     * 报表输出时,计算上一周,下一周,本月三种时间范围
     * @param begin_date
     * @param op next,up,all
     * @return
     */
    public static ArrayList calculateDays(String begin_date,String op) {
        ArrayList days = new ArrayList();
        Date start = TimeUtils.stringToDate(begin_date,"yyyy-MM-dd");
        GregorianCalendar startGC = new GregorianCalendar();
        startGC.setTime(start);
        int month = startGC.get(Calendar.MONTH);
        if("next".equals(op)) {
            for(int i=0;i<7;i++) {
                days.add(startGC.getTime());
                startGC.add(Calendar.DATE,1);
                if(month != startGC.get(Calendar.MONTH))
                    break;
            }
        }else if("up".equals(op)) {
            startGC.add(Calendar.DATE,-7);
            for(int i=0;i<7;i++) {
                startGC.add(Calendar.DATE,1);
                if(month != startGC.get(Calendar.MONTH))
                    continue;
                days.add(startGC.getTime());
            }
        }else if("all".equals(op)) {
            start = TimeUtils.stringToDate( TimeUtils.dateToString(start,"yyyy-MM")+"-01","yyyy-MM-dd");
            startGC.setTime(start);
            while(month == startGC.get(Calendar.MONTH)) {
                days.add(startGC.getTime());
                startGC.add(Calendar.DATE,1);
            }
        }
        return days;
    }
    
    /**
     * 给定时间范围标志,计算起始日期与结束日期
     * 今天,本周,本月,本季度,本年,上周,上月,上季度,去年,自定义
     * @param flag
     * @return
     */
    public String[] getBeginEnd(String queryTime,String start,String end) {
        String[] temp = new String[2];
        if("Today".equals(queryTime)) {
            temp[0] = TimeUtils.getTime("yyyy-MM-dd");
            temp[1] = temp[0];
        }else if("CurrentWeek".equals(queryTime)) {
            temp[0] = TimeUtils.getBeginOfWeek();
            temp[1] = TimeUtils.getEndOfWeek();
        }else if("CurrentMonth".equals(queryTime)) {
            temp[0] = TimeUtils.getTime("yyyy-MM")+"-01";
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime( TimeUtils.stringToDate(temp[0],"yyyy-MM-dd") );
            gc.add(GregorianCalendar.MONTH,1);
            gc.add(GregorianCalendar.DATE,-1);
            temp[1] = TimeUtils.dateToString( gc.getTime(),"yyyy-MM-dd" );
        }else if("CurrentSeason".equals(queryTime)) {
            temp[0] = TimeUtils.getBeginOfSeason();
            temp[1] = TimeUtils.getEndOfSeason();
        }else if("CurrentYear".equals(queryTime)) {
            temp[0] = TimeUtils.getTime("yyyy")+"-01-01";
            temp[1] = TimeUtils.getTime("yyyy")+"-12-31";
        }else if("LastWeek".equals(queryTime)) {
            temp[0] = TimeUtils.getBeginOfLastWeek();
            temp[1] = TimeUtils.getEndOfLastWeek();
        }else if("LastMonth".equals(queryTime)) {
            temp[0] = TimeUtils.getBeginOfLastMonth();
            temp[1] = TimeUtils.getEndOfLastMonth();
        }else if("LastSeason".equals(queryTime)) {
            temp[0] = TimeUtils.getBeginOfLastSeason();
            temp[1] = TimeUtils.getEndOfLastSeason();
        }else if("LastYear".equals(queryTime)) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime( new Date() );
            gc.add(GregorianCalendar.YEAR,-1);
            temp[0] = TimeUtils.dateToString( gc.getTime(),"yyyy")+"-01-01";
            temp[1] = TimeUtils.dateToString( gc.getTime(),"yyyy")+"-12-31";
        }else{
            temp[0] = start;
            temp[1] = end;
        }
        return temp;
    }
    
    /**
     * ==========================
     * 以下方法与日程表有关
     * ==========================
     */
    //某天所在周的第一天(从星期一开始)
    public static String getBeginOfWeek(String date) {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( TimeUtils.stringToDate(date,"yyyy-MM-dd") );
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 1) {
                break;
            }
            gc.add(GregorianCalendar.DATE,-1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    //某天所在周的最后一天(星期天)
    public static String getEndOfWeek(String date) {
        String temp = "";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( TimeUtils.stringToDate(date,"yyyy-MM-dd") );
        for(int i=1;i<=7;i++) {
            int a = gc.get(GregorianCalendar.DAY_OF_WEEK)-1;
            if(a == 0) {
                break;
            }
            gc.add(GregorianCalendar.DATE,1);
        }
        temp = dateToString(gc.getTime(),"yyyy-MM-dd");
        return temp;
    }
    
    //某月第一天
    public static String getBeginOfMonth(String date) {
        return date+"-01";
    }
    
    //某月最后一天
    public static String getEndOfMonth(String date) {
        String temp = "";
        date = date+"-01";
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( TimeUtils.stringToDate(date,"yyyy-MM-dd") );
        gc.add(GregorianCalendar.MONTH,1);
        gc.add(GregorianCalendar.DATE,-1);
        temp = TimeUtils.dateToString( gc.getTime(),"yyyy-MM-dd" );
        return temp;
    }
    
    //判断指定的时分是否在某小时内
    public static boolean isInHour(int stard,Date time) {
        boolean ok = false;
        if(time==null)
            return false;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(time);
        int a = gc.get(GregorianCalendar.HOUR_OF_DAY);
        if(a == stard)
            ok = true;
        return ok;
    }
    
    //判断2天是否是同一天
    public static boolean isInDay(Date day,Date time) {
        boolean ok = false;
        if(time==null || day==null)
            return false;
        String a = TimeUtils.dateToString(day,"yyyy-MM-dd");
        String b = TimeUtils.dateToString(time,"yyyy-MM-dd");
        if(a!=null && b!=null && a.equals(b)) {
            ok = true;
        }
        return ok;
    }
    
    //判断给定日期是否在指定月份之后
    public static boolean isInMonth(Date day,Date endOfMonth) {
        boolean ok = false;
        if(day==null || endOfMonth==null)
            return false;
        if(endOfMonth.after(day) || TimeUtils.isInDay(day,endOfMonth)) {
            ok = true;
        }
        return ok;
    }
    
    public static void main(String[] args) {
        System.out.println(getEndOfWeek());
    }
    
    /**
     * 计算2个月份之间的 1号 到结束月份31日的 时间差
     * @param year1
     * @param month1
     * @param year2
     * @param month2
     * @return
     */
    public static int betweenDays(String year1,String month1,String year2,String month2)
    {
    	  Calendar c = Calendar.getInstance();
    	  c.set(c.YEAR,Integer.parseInt(year1));//设置年
		  c.set(c.MONTH, Integer.parseInt(month1)-1);//设置月
		  c.set(Calendar.DAY_OF_MONTH, 1);//本月第一天
	      Date start=c.getTime();
	      
		  c = Calendar.getInstance();
		  c.set(c.YEAR,Integer.parseInt(year2));//设置年
		  c.set(c.MONTH, Integer.parseInt(month2)-1);//设置月
	      c.add(Calendar.MONTH, 1);//本月最后一天
	      c.set(Calendar.DAY_OF_MONTH, 1);
	      c.add(Calendar.DAY_OF_MONTH, -1);
	      Date end=c.getTime();
	      return subDateByDay(start, end)+1;
    }
}
