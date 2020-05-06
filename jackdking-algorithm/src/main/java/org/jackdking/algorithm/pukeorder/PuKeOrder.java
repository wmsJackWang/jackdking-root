package org.jackdking.algorithm.pukeorder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuKeOrder {
	 public List<String> getOriginOrder(List<String> list) {
	        int len = list.size();
//	        List<String> res = Lists.newArrayListWithCapacity(len);
	        List<String> rest =new ArrayList<String>();
	        List<String> res  = new ArrayList<String>(rest);
	        
	        for (int i = len - 1; i >= 0; i--) {
	            end2start(res);//将对尾牌放入到队头
	            res.add(0,list.get(i));//再从另一个队列的  队尾开始到队头拿出牌到  该队列队头
	        }
	        return res;
	    }
	 //将队尾牌放到队头
	    private void end2start(List<String> res) {
	        if(res == null || res.size() <= 0){
	            return;
	        }
	        String temp = res.get(res.size()-1);
	        res.remove(temp);
	        res.add(0,temp);
	    }//ace    bdf
	 
	    public static void main(String[] args) {
	        List<String> stringst = Arrays.asList("A", "B", "C", "D", "E", "F");
	        List<String> strings  = new ArrayList<String>(stringst);
	        PuKeOrder puKeOrder = new PuKeOrder();
	        List<String> originOrder = puKeOrder.getOriginOrder(strings);
	        System.out.println(originOrder);
	 
	    }
}
