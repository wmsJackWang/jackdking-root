package org.jackdking.shardjdbcyaml.algorithm;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Random;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.util.DigestUtils;

/**
 * 
 * @ClassName: Super9000TableShardingAlgorithm
 * @Description 精准查询
 * @author:luchenxi
 * @date: 2019年11月13日 上午10:02:30
 *
 *             注意：本内容仅限内部传阅，禁用于其他的商业目
 */
public class SuperTableShardingAlgorithm implements PreciseShardingAlgorithm<Long>
{

	/**
	 * 
	 * <p> Title: doSharding </p> <p> Description: 精确查询 IN = </p>
	 * 
	 * @param availableTargetNames
	 * @param shardingValue
	 * @return
	 * @see io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm#doSharding(java.util.Collection,
	 *      io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue)
	 */
	@ Override
	public String doSharding(Collection<String> availableTargetNames , PreciseShardingValue<Long> shardingValue)
	{
		System.out.println("精确分表策略生效...");
		String bussinessOrderId = shardingValue.getValue().toString();//业务方的订单号
		if(bussinessOrderId != null)
		{
			// 查询所在周的周一日期后缀
			String mondayStr = getShardingValue(bussinessOrderId); 
			System.out.println("目标表后缀 : " + mondayStr);
			for(String each : availableTargetNames)
			{
				if(each.endsWith(mondayStr))
				{
					System.out.println("实际表 : " + each);
					return each;
				}
			}
		}
		throw new IllegalArgumentException();
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		   String random = getMD5("DA112434354068904823Aaadfdfdfdfdf15");//1631659253101783393684335719623985   6537497330365575379774450529845286931782970345057771873
		   											 //1631659253101783393684335719623985   6537497330365575379774450529845286931782970345057771873
		    int i = 0,j = 0;
		   for(;;)
		   {
			    random = getMD5(String.valueOf(System.currentTimeMillis() + new Random().nextInt()));
			    String bi = new BigInteger(random.getBytes("UTF-8")).toString();
			    BigInteger MODULE = BigInteger.valueOf(1024);
			    BigInteger MODULE1 = BigInteger.valueOf(2);
			    BigInteger numBig = new BigInteger(bi);
			    int  modValue = numBig.mod(MODULE).intValue();
			    System.out.println(modValue);
			    System.out.println(numBig.mod(MODULE1).intValue());
			    if(numBig.mod(MODULE1).intValue()==0)++i;
			    else ++j;
			    
			    if(j>10000)break;
		   }
		   
		   System.out.println(i+":"+j);
	}
	
	//盐，用于混交md5
//	private static final String slat = "&%5123***&&%%$$#@";
	
	public static String getShardingValue(String random) {
		String bi = "";
		try {
			bi = new BigInteger(random.getBytes("UTF-8")).toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigInteger MODULE = BigInteger.valueOf(1024);
		BigInteger MODULE1 = BigInteger.valueOf(2);
		BigInteger numBig = new BigInteger(bi);
		int  modValue = numBig.mod(MODULE1).intValue();
		System.out.println(modValue);
		System.out.println(numBig.mod(MODULE).intValue());
		return modValue+"";
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