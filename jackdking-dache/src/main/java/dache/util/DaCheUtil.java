package dache.util;

import java.util.Random;

public class DaCheUtil {
	
	/**
	 * Hello world!
	 * 北京市界的地理坐标为：北纬39°28’至41°03’,东经115°25’至117°35’
	 * 值区间是  142080  --  147780      415500 -- 423300
	 * 
	 * 1. 根据北京的经纬度区间，设置北京这边的服务的边界。
	 * 2. 制定经纬度网络，设定网格的单位长度，每个网格点作为乘客行程单的队列数据。
	 * 		赤道长40000km，在赤道上东西、南北方向每秒角度对应于30.86m。
			4*10^7m/(360*60*60)=30.86m/sec。
	 * 
	 * 3. 北京维度范围是5700'' ,网格节点距离是40'' , 因此有142.5个长度网格
	 * 
	 * 4. 北京经度范围是7800'',网格节点间距是40'', 因此有195个宽度网格。
	 * 
	 * 
	 * 
	 * 
	 * 
	 **/
	public static int lati = 40*60*60;//测试使用的纬度值
	public static int lontu = 116*60*60;//测试使用的经度值
	
	public final static int STEP_LENGTH = 40;//网格模型的网格步长，单位 秒。
	public final static String NET_POINT_PREFIX = "net_point";//站点key的前缀
	public final static String KEY_SPLIT = ":";//redis key的间隔符
	
	//latitude:维度  ， longitude:经度
	//根据传入的经纬度数值，得到它对应的网格节点队列
	public static String getNetPointKey(int latitude , int longitude) {
		
		Long lat =(long) Math.ceil(latitude*1.0/STEP_LENGTH) ;
		
		Long lon = (long) Math.ceil(longitude*1.0/STEP_LENGTH);
		
		return NET_POINT_PREFIX+KEY_SPLIT+lon+KEY_SPLIT+lat;
		
	}
	
	
	
	//测试
	//随机获取定量范围内的经纬度信息
	public static int getLatitu_lontude(int num1 , int num2) {
		
		Random random=new Random();
		int top = random.nextInt(num2)%(num2-num1+1) + num1;
		return top;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(getLatitu_lontude(lati, lati));
		System.out.println(getLatitu_lontude(lontu, lontu));
		
		System.out.println(getNetPointKey(getLatitu_lontude(lati, lati),getLatitu_lontude(lontu, lontu)));
	}
	
}
