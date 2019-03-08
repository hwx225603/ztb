package com.xiaour.spring.boot.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	static long nd = 1000 * 24 * 60 * 60;
	static long nh = 1000 * 60 * 60;
	static long nm = 1000 * 60;

	public static String getDateX(Date startDate, Date nowDate) {
		long diff = nowDate.getTime() - startDate.getTime();
		// 计算差多少分钟
		long min = diff / nm;
		if(min > 60) {
			// 计算差多少小时
			long hour = diff / nh;
			if(hour > 24) {
				// 计算差多少天
				long day = diff / nd;
				return day + "天前" ;
			}else {
				return  hour + "小时前";
			}
		}else {
			return  min + "分钟前";
		}
	}
	
	public static Date addDateDay(int hour){   
        Date date = new Date();   
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.HOUR, hour);// 24小时制   
        date = cal.getTime();   
        return date;   
	}
	
	public static Date addDateMinut(int minut){   
        Date date = new Date();   
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.MINUTE, minut);
        date = cal.getTime();   
        return date;   
	}
}
