package org.jackdking.algorithm.bytedance.last;

import java.util.HashMap;
import java.util.Map;

public class FindLianXuSonStr {

    public static void main(String[] args) {

    }
    //动态规划+哈希表
    public int lengthOfLongestSubstring_hash(String s) {
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
