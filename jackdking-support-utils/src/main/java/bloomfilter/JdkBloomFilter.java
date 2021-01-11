package bloomfilter;

import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class JdkBloomFilter {
	
	
	   /**假设数据库目前有订单共计1百万个*/
    public static Integer orderNums = 1000000;
	
	public static void main(String[] args) {
		
	
		/**创建一个布隆过滤器对象，可预测的误判率fpp值为0.03，默认就是这个值,误判率越低，需要的位图size*/
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),
        		JdkBloomFilter.orderNums, 0.0000003D);

        List<String> uuidQueryList = new ArrayList<>();
        Set<String> uuidQuerySet = new HashSet<>();
        long start  = System.currentTimeMillis();
        // 生成100w个uuid，分别放到三个容器中
        for (int i = 0; i < JdkBloomFilter.orderNums; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            uuidQuerySet.add(uuid);
            uuidQueryList.add(uuid);
        }
        long end = System.currentTimeMillis();
        System.out.println("(随机)初始化100w个订单号耗时："+(end-start)+"ms");

        // 布隆过滤器正确判断的订单号个数
        int correct = 0;
        // 布隆过滤器错误判断的订单号个数
        int wrong = 0;
        // 假设，测试的订单号数量为1w个
        int testNum = 10000;
        String uuidTest;
        for (int i = 0; i < testNum; i++) {
            // 从已有的100w个订单中取出100个
            uuidTest = i % 100 == 0 ? uuidQueryList.get(i) : UUID.randomUUID().toString();
            // 开始判断,先布隆过滤器来一波
            if (bf.mightContain(uuidTest)) {
                //如果集合中真的存在，那就是真的存在了
                if (uuidQuerySet.contains(uuidTest)) {
                    correct++;
                } else {
                    wrong++;
                }
            }
        }
        System.out.println("================================");
        System.out.println("在已知已存在的100w个订单号中，从其中选100个已存在的订单号，其中布隆过滤器判断确确实实存在的数量有【"+correct+"】个。");
        System.out.println("在已知已存在的100w个订单号中，外界随机生成9900个新订单号，其中布隆过滤器误判存在的数量有【"+wrong+"】个，挡住的恶意订单号查询数量有："+(testNum-wrong)+"个");
        NumberFormat percent = NumberFormat.getPercentInstance();
        // 保留两位小数
        percent.setMaximumFractionDigits(2);
        float wp = (float)wrong/(testNum-100);
        float rp = (float) (testNum-100-wrong) /(testNum-100);
        System.out.println("================================");
        System.out.println("得出结论如下：");
        System.out.println("布隆过滤器的正确率（抵挡住的）："+percent.format(rp));
        System.out.println("布隆过滤器的误报率（没有抵挡住的）："+percent.format(wp));
		
		
	}

}
