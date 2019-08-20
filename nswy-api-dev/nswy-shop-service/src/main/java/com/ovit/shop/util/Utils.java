package com.ovit.shop.util;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils
{

	public static String builderRandomString(int digit)
	{
		 String range = "0123456789";
		return builderRandomString(digit,range);
	}
	public static String builderRandomString(int digit, String range) 
	{ 
	    //String range = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuilder sb = new StringBuilder();   
	    for (int i = 0; i < digit; i++) 
	    {   
	        int number = random.nextInt(range.length());   
	        sb.append(range.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	
	public static String decimalFormat(Object object, String pattern)
	{
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(object);
	}

	

  	
  	public static String getDateStringByPattern(String pattern) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);//"yyyy-MM-dd HH:mm:ss"
		sDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		  
		return sDateFormat.format(new Date());
	}
	

	
	
	/**
	 * 转换为Map<String, Object>
	 * 
	 * @param Object
	 * @return
	 */
	public static Map<String,Object>toMapFromObject(Object obj){
		  Map<String,Object> reMap = new HashMap<String,Object>();
		  if (obj == null)
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
		   for(int i=0;i<fields.length;i++){
		    try {
		     Field f = obj.getClass().getDeclaredField(fields[i].getName());
		     f.setAccessible(true);
		           Object o = f.get(obj);
		           reMap.put(fields[i].getName(), o);
		    } catch (NoSuchFieldException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalArgumentException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    } catch (IllegalAccessException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		   }
		  } catch (SecurityException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  return reMap;
	}
	
	public static String trim(String str)
	{
		if(str == null)
		{
			return "";
		}
		
		return str.trim();
	}
	
	 public static String listToString(List<String> list, String separator) {  
		   StringBuilder sb = new StringBuilder();  
		   for (String s : list) {  
		       if (s != null && !"".equals(s)) {  
		           sb.append(s).append(separator);  
		       }  
		   }  
		   sb.deleteCharAt(sb.length()-1);
		   return sb.toString();  
	}  

	 public static String formatTime(Date date){
		 String time = "";
		 if(date != null){
			 SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmss");
			 time = sm.format(date);
		 }
		 return time;
	 }

	
	/**
	 * 比较2个字符串时间差，返回秒 ：20160120163016
	 * @param time1	字符串时间
	 * @param time2	字符串时间
	 * @return
	 */
	public static long compareTime(String time1,String time2){
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date1 = sf.parse(time1);
			Date date2 = sf.parse(time2);
			long t1 = date1.getTime();
			long t2 = date2.getTime();
			return (t1-t2)/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 格式换时间，将20160120163016 格式换成2016-01-20 16:30:16
	 * @param time	字符串
	 * @return
	 */
	public static String formatTime(String time){
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sf.parse(time);
			String str = sf2.format(date);
			return str;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parsePercent(float number){
		DecimalFormat df = new DecimalFormat("0.000");
		String  s = df.format(number);
		return s;
	}


	/**
	 * 根据偏移量，获取当天的日期值
	 * @param str
	 * @return
	 */
	public static String getCal(int ind){
		Calendar cal = Calendar.getInstance();
		Date curr = null;
		SimpleDateFormat dateFormater_t = new SimpleDateFormat("yyyyMMdd");
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + ind);	
		curr = cal.getTime();
		String dayStr = dateFormater_t.format(curr);
		return dayStr;
	}
	/**
	 * 生成UUID
	 *
	 * @return string
	 */
	public static String randomStringByUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
