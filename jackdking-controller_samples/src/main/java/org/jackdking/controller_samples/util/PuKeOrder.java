package org.jackdking.controller_samples.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuKeOrder {
	 public List<String> getOriginOrder(List<String> list) {
	        int len = list.size();
//	        List<String> res = Lists.newArrayListWithCapacity(len);
	        List<String> rest = Arrays.asList("");
	        List<String> res  = new ArrayList<String>(rest);
	        
	        for (int i = len - 1; i >= 0; i--) {
	            end2start(res);
	            res.add(0,list.get(i));
	        }
	        return res;
	    }
	 
	    private void end2start(List<String> res) {
	        if(res == null || res.size() <= 0){
	            return;
	        }
	        String temp = res.get(res.size()-1);
	        res.remove(temp);
	        res.add(0,temp);
	    }//ace    bdf
	 
	    public static void main(String[] args) {
	        List<String> stringst = Arrays.asList("A", "C", "E", "B", "F", "D");
	        List<String> strings  = new ArrayList<String>(stringst);
	        PuKeOrder puKeOrder = new PuKeOrder();
	        List<String> originOrder = puKeOrder.getOriginOrder(strings);
	        System.out.println(originOrder);
	 
	    }
}
