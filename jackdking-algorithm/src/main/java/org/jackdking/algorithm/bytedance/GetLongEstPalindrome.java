package org.jackdking.algorithm.bytedance;

import org.codehaus.groovy.util.StringUtil;
import org.junit.platform.commons.util.StringUtils;

//https://www.nowcoder.com/practice/b4525d1d84934cf280439aeecc36f4af?tpId=196&tqId=37122&rp=1&ru=/exam/company&qru=/exam/company&sourceUrl=%2Fexam%2Fcompany&difficulty=undefined&judgeStatus=undefined&tags=&title=
/*
 *
 *
 * 描述
    对于长度为n的一个字符串A（仅包含数字，大小写英文字母），请设计一个高效算法，计算其中最长回文子串的长度。
    数据范围： 1≤n≤1000
    要求：空间复杂度 O(1)O(1)，时间复杂度 O(n^2)O(n
    2
     )
    进阶:  空间复杂度 O(n)O(n)，时间复杂度 O(n)O(n)
    示例1
    输入：
    "ababc"
    复制
    返回值：
    3
    复制
    说明：
    最长的回文子串为"aba"与"bab"，长度都为3
 */
public class GetLongEstPalindrome {

    public static void main(String[] args) {

        int longVal = getLongestPalindrome("11011101111");
        System.out.println("longVal = " + longVal);

        //动态规划
        longVal = getLongestPalindrome0409("11011101111");
        System.out.println("longVal = " + longVal);

        //动态规划
        longVal = getLongestPalindrome0416("11011101111");
        System.out.println("longVal = " + longVal);
    }

    private static int getLongestPalindrome0416(String s) {
        if (s==null){
            return 0;
        }
        if (s.length() < 2){
            return s.length();
        }
        int maxLen = 0;
        boolean dp[][] = new boolean[s.length()][s.length()];
        for (int i = 0 ;i<s.length();i++) {
            for (int j =0; j < s.length();j++){
                if (s.charAt(j) != s.charAt(i)) {
                    continue;//如果不相等，则没必要再判断
                }

                if (j == i) {
                    dp[j][i] = true;
                }else if (i - j <3) {
                    dp[j][i] = true;
                } else {
                    dp[j][i] = dp[j+1][i-1];
                }

                if (dp[j][i]&& i - j + 1 > maxLen) {
                    maxLen = i - j + 1;
                }
            }
        }
        return maxLen;

    }







    private static int getLongestPalindrome0409(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {//一个字符串或者空字符串
            return s.length();
        }
        boolean[][]dp = new boolean[s.length()][s.length()];
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j <= i; j++){
                if (s.charAt(i) != s.charAt(j)) {
                    continue;
                }

                if (j == i) {
                    dp[j][i] = true;
                }else if (i - j < 3) {
                    dp[j][i] = true;
                }else {
                    dp[j][i] = dp[j+1][i-1];
                }

                if (dp[j][i]&& i - j + 1 > maxLen) {
                    maxLen = i - j + 1;
                }

            }
        }
        return maxLen;

    }

    private static int getLongestPalindrome(String str) {

        if (StringUtils.isBlank(str) || str.length() < 2){
            return  1;
        }
        int maxLen = 0;
        int val1 = 0, val2 = 0;
        for (int i = 0; i < str.length(); i++) {
            //两种情况，回文串长度是奇数或偶数
            //第一种
            val1 = caculatePalindromeLen(str, i, i);
            val2 = caculatePalindromeLen(str, i, i+1);
            maxLen = Math.max(maxLen, Math.max(val1, val2));

        }
        return maxLen;
    }

    private static int caculatePalindromeLen(String str, int i, int j) {
        while (i>=0 && j<str.length()) {
            if (str.charAt(i) != str.charAt(j)) {
                break;
            }
            i--;
            j++;
        }
        return j-i-1;
    }

}
