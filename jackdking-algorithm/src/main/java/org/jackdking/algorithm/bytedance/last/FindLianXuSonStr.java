package org.jackdking.algorithm.bytedance.last;

import java.util.HashMap;
import java.util.Map;

/*
 * 最长不重复字符的子串
 */
public class FindLianXuSonStr {

    public static void main(String[] args) {

        String data = "abcacbdefytux";
        int res = lengthOfLongestSubstring_hash(data);
        System.out.println("最长不重复子串:"+res);

        //hash+线性规划
        res = getMaxNoRepeatSonStr(data);
        System.out.println("最长不重复子串:"+res);

        res = getMaxNoRepeatSonStrWindow(data);
        System.out.println("最长不重复子串:"+res);

        res = getMaxNoRepeatSonStrWindow20231007(data);
        System.out.println("最长不重复子串:"+res);

    }

    private static int getMaxNoRepeatSonStrWindow20231007(String s) {
        if (s== null||s.length() ==0){
            return 0;
        }

        int l  = 0, r = 0;
        int res = 0, cur = 0;
        HashMap<Character, Integer> hash = new HashMap<>();
        hash.put(s.charAt(l), l);
        Integer curL = 0;
        while (l < s.length() && r < s.length() && l <= r) {
            curL = hash.get(s.charAt(r));
            if (curL != null) {
                for (int i = l; i <= curL ; i++){
                    hash.remove(s.charAt(i));
                }

                l = curL + 1;
            }

            hash.put(s.charAt(r), r);

            if (r - l + 1 > res) {
                res = r - l + 1;
            }
            r ++;

        }
        return res;
    }

    private static int getMaxNoRepeatSonStrWindow(String s) {

        if (s==null ||s.length()==0) {
            return 0;
        }
        int l = 0, r = 1, wl = 0;
        HashMap<Character, Integer> hash = new HashMap<>();
        hash.put(s.charAt(0), 0);
        int res = 1;
        while (l < s.length() && r< s.length() && l <= r) {

            if (hash.get(s.charAt(r))!=null) {
                wl = hash.get(s.charAt(r))+1;
                System.out.println(l + ":" + r + ":" + (r-l+1));
            }
            for (int i = l; i < wl ; i++) {
                hash.remove(s.charAt(i));
            }
            l = wl;
            hash.put(s.charAt(r), r);

            res = Math.max(res, r - l+1);
            r++;
        }

        return res;

    }

    private static int getMaxNoRepeatSonStr(String s) {

        HashMap<Character, Integer> hash = new HashMap<>();
        int temp = 0 , res = Integer.MIN_VALUE, m;
        for (int i = 0; i < s.length() ; i++) {
            m = hash.getOrDefault(s.charAt(i), -1);
            hash.put(s.charAt(i), i);//每个字符串的最新索引位置
            if (i-m > temp) {
                //最新不重复子串基础上++
                temp++;
            }else {
                //充值最新不重复子串长度。
                temp = i-m;
            }
            res = Math.max(temp, res);
        }
        return res;
    }

    //动态规划+哈希表
    public static int lengthOfLongestSubstring_hash(String s) {
        //哈希表存储字符的位置
        Map<Character,Integer> hsp = new HashMap<>();
        int res = 0,temp = 0;
        for(int i=0;i<s.length();i++){
            //之前存在直接是之前的位置值，否则赋值-1
            int m = hsp.getOrDefault(s.charAt(i),-1);
            hsp.put(s.charAt(i),i);
            //这个字符不重复，在这个排列长度中加1
            if(i-m>temp){
                temp++;
            }
            //长度变为两个重复字符的位置差
            else{
                temp = i - m;
            }
            res = Math.max(res,temp);
        }
        return res;
    }

    //动态规划+线性遍历
    public int lengthOfLongestSubstring_line_scan(String s) {
        Map<Character, Integer> dic = new HashMap<>();
        int res = 0, tmp = 0;
        for(int j = 0; j < s.length(); j++) {
            int i = j - 1;
            //查找j之前的字符，直到有一个重复
            while(i >= 0 && s.charAt(i) != s.charAt(j)) i--;
            //有重复tmp为j-i，无重复tmp加1
            tmp = tmp < j - i ? tmp + 1 : j - i;
            res = Math.max(res, tmp);
        }
        return res;
    }

    //  双指针+哈希表
    public int lengthOfLongestSubstring_two_point(String s) {
        Map<Character, Integer> dic = new HashMap<>();
        int i = -1, res = 0;
        for(int j = 0; j < s.length(); j++) {
            //存在重复的字符了，更新i的位置为重复字符的位置，或者i的最大值（abba）
            if(dic.containsKey(s.charAt(j)))
                i = Math.max(i, dic.get(s.charAt(j)));
            dic.put(s.charAt(j), j); // 哈希表记录
            res = Math.max(res, j - i); // 更新结果
        }
        return res;
    }

}
