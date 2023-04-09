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

        int longVal = getLongestPalindrome("1212");
    }

    private static int getLongestPalindrome(String str) {

        if (StringUtils.isBlank(str) || str.length() < 2){
            return  1;
        }
        int maxLen = 0;
        for (int i = 0; i < str.length(); i++) {
            //两种情况，回文串长度是奇数或偶数
            //第一种

        }
    }

}
