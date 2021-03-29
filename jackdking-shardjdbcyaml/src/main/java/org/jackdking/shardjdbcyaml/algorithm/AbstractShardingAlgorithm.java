package org.jackdking.shardjdbcyaml.algorithm;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.springframework.util.DigestUtils;

public abstract class AbstractShardingAlgorithm {
	
	
	private static int maxTableNum = 2048;//系统预留的表数量
	private static int dbNum = 2;
	private static int tableNum = 2;
	private static int totalTableNum = dbNum*tableNum;
	//盐，用于混交md5
//	private static final String slat = "&%5123***&&%%$$#@";
	
	public static String getDbShardingValue(String random) {
		String bi = "";
		try {
			bi = new BigInteger(random.getBytes("UTF-8")).toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigInteger db_table_info = BigInteger.valueOf(maxTableNum);
		BigInteger numBig = new BigInteger(bi);
		int  modValue = numBig.mod(db_table_info).intValue();//预留的最大值
		
		int resModValue = modValue%totalTableNum;
		
		
		System.out.println(modValue);
		System.out.println(resModValue);
		return resModValue/tableNum +"";
	}
	
	public static String getTableShardingValue(String random) {
		String bi = "";
		try {
			bi = new BigInteger(random.getBytes("UTF-8")).toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigInteger MODULE = BigInteger.valueOf(maxTableNum);
		BigInteger numBig = new BigInteger(bi);
		int  modValue = numBig.mod(MODULE).intValue();//预留的最大值
		
		System.out.println(modValue);
		System.out.println(numBig.mod(MODULE).intValue());
		
		return modValue%tableNum+"";
	}
	
	
	/**
	 * 生成md5
	 * @param seckillId
	 * @return
	 */
	public static String getMD5(String str) {
		String md5 = DigestUtils.md5DigestAsHex(str.getBytes());
		return md5;
	}

}
