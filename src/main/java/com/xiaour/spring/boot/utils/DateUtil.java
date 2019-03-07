package com.xiaour.spring.boot.utils;

import java.util.Date;

public class DateUtil {
	
	static long nd = 1000 * 24 * 60 * 60;
	static long nh = 1000 * 60 * 60;
	static long nm = 1000 * 60;

	public static String getDateX(Date startDate, Date nowDate) {
		long diff = nowDate.getTime() - startDate.getTime();
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		if(min > 60) {
			// 计算差多少小时
			long hour = diff % nd / nh;
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
}
