package com.zhuzhi.spring.cloud.alibaba.freeswitch.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CommonUtils {
	
	/**
	 * 动态组装sql（解决where1=1低效的方案） 
	 * @param sqlBuilder	Sql 组装器
	 * @param hasWhere		是否已经包含 'where' 
	 * @param condition		查询条件
	 * @return Boolean		true : 已经有 'where'
	 */
	public static Boolean appendWhereIfNeed(StringBuilder sqlBuilder, Boolean hasWhere, String condition){
        if(hasWhere == false){  
        	sqlBuilder.append(" where ");  
        	sqlBuilder.append(condition);  
        }else{  
        	sqlBuilder.append(" and ");
        	sqlBuilder.append(condition);
        }  
        return true;
    } 
    
	/**
	 * 获取字符串的实际值,如果字符串为空，''都视为null;
	 * @param val		传入字符串的值
	 * @return
	 */
	public static String findStringNullVal(String val){
		if(StringUtils.isEmpty(val)){
			return null;
		}else{
			return val.trim();
		}
	}
	
	/**
	 * 获取随机数
	 * @param maxNumber	最大值，不包括该最大值
	 * @return
	 */
	public static int findRamdomNumber(int maxNumber){
		Random random = new Random();
		return random.nextInt(maxNumber);
	}
	
	/**
	 * 获取6位随机数，之后前面补零
	 * @return
	 */
	public static String findSixRamdomNumberSupplyZero(){
		return String.format("%06d",findRamdomNumber(1000000));
	}
	
	
	 /**
     * get postfix of the path
     * @param path
     * @return
     */
  /*  public static String getPostfix(String path) {
        if (path == null || ConstantsUtils.EMPTY.equals(path.trim())) {
            return ConstantsUtils.EMPTY;
        }
        if (path.contains(ConstantsUtils.POINT)) {
            return path.substring(path.lastIndexOf(ConstantsUtils.POINT) + 1, path.length());
        }
        return ConstantsUtils.EMPTY;
    }*/
    
    /**
     * 获取现在年月字符串	 无下划线
     * @return		格式yyMM 例如:1503
     */
    public static String findNowMarking(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
    	return sdf.format(new Date());
    			
    }
    
    /**
     * 获取现在年月字符串	 无下划线
     * @return		格式yyyyMM 例如:201504
     */
    public static String findNowMthStrings(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    	return sdf.format(new Date());
    			
    }
    
    /**
     * 获取现在年月日字符串	 无下划线
     * @return		格式yyyyMMdd 例如:20150301
     */
    public static String findNowDayString(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(new Date());
    			
    }
    
    /**
     * 根据公海Id获取table后缀，不带前下划线
     * @param openSeaId
     * @return		例如:1503
     */
    public static String findTableSuffixByOpenSeaId(String openSeaId){
    	return findTableSuffix(openSeaId);
	}
    
    /**
     * 根据客服记录Id获取table后缀，不带前下划线
     * @param recordId
     * @return
     */
    public static String findTableSuffixByRecordId(String recordId){
    	return findTableSuffix(recordId);
    }
    
    private static String findTableSuffix(String suffixId){
    	if(StringUtils.isNotBlank(suffixId)){
    		suffixId = suffixId.trim();
			if(suffixId.length() > 4){
				return suffixId.substring(0,4);
			}else{
				return null;
			}
		}else{
			return null;
		}
    }
    
    /**
     * 获取当前table后缀，不带前下划线
     * @param openSeaId
     * @return		例如:201503
     */
    public static String findNowTableSuffix(){
		return findNowDayString();
	}
    
    /**
     * 根据电话号码，获取号码对应的表后缀
     * @param telNumber
     * @return
     */
    public static String findTelphoneSuffix(String telNumber){
		String index = "";
		if(StringUtils.isNotEmpty(telNumber)){
			index = telNumber.trim().substring(telNumber.trim().length() - 1);
		}
		return index;
	}
    
    /**
     * 获取加密之后的电话
     * 
     * 15861692130-->158
     * 
     * @param telNumber
     * @return
     */
    public static String encryptTelNumber(String telNumber) {//原Ec2加密方法
		try {
			if(telNumber == null) {return "";}
			int length = telNumber.length();
			if(length >= 11){
				if(!telNumber.startsWith("0")) {
					return telNumber.substring(0, 3)+"******"+telNumber.substring(9);
				} else {
					return telNumber.substring(0, 4)+"******"+telNumber.substring(10);
				}
			} else if(length > 8) {
				return telNumber.substring(0, telNumber.length()-7)+"****"+telNumber.substring(telNumber.length()-3);
			} else if(length >= 7){
				return telNumber.substring(0, 2)+"****"+telNumber.substring(6);
			} else {
				return telNumber;
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String findEncryptTelNumber(String telephone) {
		try {
			if(StringUtils.isNotEmpty(telephone) && StringUtils.isNotEmpty(telephone.trim())){
				telephone = telephone.trim();
				List<String> ls = new ArrayList<String>();
				if(telephone.indexOf(",") > 0){
					String[] tnarr = telephone.split(",");
					for (int i = 0; i < tnarr.length; i++) {
						String tn = tnarr[i];
						tn = encryptTelNumber(tn);
						ls.add(tn);
					}
				}else{
					ls.add(telephone);
				}
				return StringUtils.join(ls,",");
			}else{
				return "";
			}
		} catch (Exception e) {
			return "";
		}
		
	}
	
	/**	
	 * 获得第二个号码,加零或不加零
	 * @param firstNumber 	原始第一个号码，如果以0开头返回0以后的号码,否则加0返回
	 * @return null或者号码
	 */
	public static String findSecondNumber(String firstNumber) {
		if(StringUtils.isNotBlank(firstNumber)){
			if(firstNumber.startsWith("0")){
				return firstNumber.substring(1,firstNumber.length());
			}else{
				return "0" + firstNumber;
			}
		}else{
			return null;	
		}
	}
	
	/**
	 * 去掉电话号码中的非数字字符
	 * 只保留数字字符
	 * 不去掉前段的0
	 * 
	 * @param contents
	 * @return
	 */
	/*public static String phoneNumberProcess(String phoneNumber) {
		// 前面的Value部分已经Trim过了，不用再次进行处理
		if (phoneNumber == null || phoneNumber.equals("")) {
			return phoneNumber;
		}
		// 去掉电话号码中的非数字字符
		char[] vala = phoneNumber.toCharArray();
		phoneNumber = "";
		
		for (char charElement : vala) { // Ascii的对应关系是0-48 9-57 数字
			if (charElement > 47 && charElement < 58) {
				phoneNumber += charElement;
			}
		}
		return phoneNumber;
		
		//去掉电话号码前面的0
//		int len = phoneNumber.length();
		int st = 0;
		char[] valb = phoneNumber.toCharArray();
		while ((st < len) && (valb[st] == '0')) {
			st++;
		}
		return phoneNumber.substring(st, len);
	}*/

	public static Integer findDirextionType(String directionTypeTxt) {
		Integer directionType = 0;//无呼叫方向
		if(StringUtils.isNotBlank(directionTypeTxt)){
			directionTypeTxt = directionTypeTxt.trim();
			if("outgoing".equals(directionTypeTxt)){
				directionType = 1;					//呼出
			}else if("incoming".equals(directionTypeTxt)){
				directionType = 2;					//呼入
			}else if("inner".equals(directionTypeTxt)){
				directionType = 3;					//内部
			}
		}else{
			directionType = 0;
		}		
		return directionType;
	}
	
	/**
	 * Aes加密,URL转码
	 * @param telNumber
	 * @return
	 */
//	@SuppressWarnings("deprecation")
	/*public static String findAesEncryptEncoder(String telNumber){
		try {
			
			System.out.println("号码|"+telNumber+"|");
			System.out.println("KEY|"+AesEncryptDecryptUtils.aesKey+"|");
			
			return URLEncoder.encode(AesEncryptDecryptUtils.aesEncrypt(telNumber, AesEncryptDecryptUtils.aesKey));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}*/
	
	/**
	 * URL解码Aes解密
	 * @param telNumber
	 * @return
	 */
//	@SuppressWarnings("deprecation")
	/*public static String findAesDecryptDecoder(String telNumber){
		try {
			return AesEncryptDecryptUtils.aesDecrypt(URLDecoder.decode(telNumber), AesEncryptDecryptUtils.aesKey);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}*/
	

	/** JIANGRH
	 * 导出 CSV 文件时，将数值格式 转成文本格式 输出
	 * 格式化待下载信息  字段值为 String 类型，并且以 英文逗号',' 结尾
	 * @param cellObj	待格式化的 字段值
	 * @return String	
	 */
	public static String formatCSVCellValue(Object cellObj) {
		if(cellObj == null) {
			return ",";
		} else if(cellObj instanceof String) {
			return "\"\t" + ((String) cellObj).replace(",", "，") + "\",";	// 前后增加 双引号，前面再加上水平制表符，这样导出CSV 时，就是文本格式 了
		} else if(cellObj instanceof Date) {
			SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return "\"\t" + sdf_day.format(cellObj) + "\",";
		} else {
			return cellObj.toString() + ",";
		}
	}
	
	/**
	 * 获取客户端真实IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 判断是否是分机
	 * @param target		判断的值
	 * @return				true  false
	 */
	public static boolean isExten(String target) {
		if(StringUtils.isBlank(target)) {
			return false;
		}
		String regex = "^(\\d{2}|[^1]\\d{2}|[^25]\\d{3}|[^91]\\d{4}|\\d{6})$";
		return target.matches(regex);
	}
	
	/** 
	 * 把毫秒数转为 天xxx时xx分xx秒
	 * 
	 * @param secondTotal
	 * @return
	 */
	public static String formatTimeTxt(long secondTotal) {
		
		if(secondTotal <= 0) {
			return "00:00:00";
		}
		
		long sec = 0;
		long min = 0;
		long hours = 0;
		long days = 0;

		String second = null;
		String minute = null;
		String hour = null;

		long c = secondTotal;
		if (c < 60) {
			sec = c;
		} else {
			sec = c % 60;
		}

		c = secondTotal / 60;
		if (c < 60) {
			min = c;
		} else {
			min = c % 60;
		}

		c = secondTotal / 60 / 60;

		if(c < 24) {
			hours = c;
		} else {
			hours = c % 24;
		}

		days = c / 24;
		
		if (sec < 10) {
			second = "0" + sec;
		} else {
			second = String.valueOf(sec);
		}

		if (min < 10) {
			minute = "0" + min;
		} else {
			minute = String.valueOf(min);
		}
		
		if (hours < 10) {
			hour = "0" + hours;
		} else {
			hour = String.valueOf(hours);
		}
		
		if(days > 0) {
			return days + "天 " + hour + ":" + minute + ":" + second + "";
		} else {
			return hour + ":" + minute + ":" + second + "";
		}
		
	}
	
	/**
	 * 获取从list到Sql语句 in使用
	 * @param list	
	 * @return	'aaaaa','bbbbb'
	 */
	public static String findListToInSql(List<String> list){
		StringBuilder sb = new StringBuilder();
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				if (i != 0) {
					sb.append(",");
					sb.append("'");
					sb.append(list.get(i));
					sb.append("'");
				} else {
					sb.append("'");
					sb.append(list.get(i));
					sb.append("'");
				}
			}
		}
		return sb.toString();
	}
}
