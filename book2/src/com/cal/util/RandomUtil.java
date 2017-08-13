package com.cal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RandomUtil {
	public static String random(){
		return UUID.randomUUID().toString();
	}
	
	public static String orderNum(){
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(now)+System.currentTimeMillis();
		return str;
	}
}
