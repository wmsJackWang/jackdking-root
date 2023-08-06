package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.springframework.util.StringUtils;

/*
 * 描述

给出两个字符串 s 和 t，要求在 s 中找出最短的包含 t 中所有字符的连续子串。

数据范围：0 \le |S|,|T| \le100000≤∣S∣,∣T∣≤10000，保证s和t字符串中仅包含大小写英文字母
要求：进阶：空间复杂度 O(n)O(n) ， 时间复杂度 O(n)O(n)
例如：
S ="XDOYEZODEYXNZ"S="XDOYEZODEYXNZ"
T ="XYZ"T="XYZ"
找出的最短子串为"YXNZ""YXNZ".

注意：
如果 s 中没有包含 t 中所有字符的子串，返回空字符串 “”；
满足条件的子串可能有很多，但是题目保证满足条件的最短的子串唯一。

示例1
输入：
"XDOYEZODEYXNZ","XYZ"
复制
返回值：
"YXNZ"
复制
示例2
输入：
"abcAbA","AA"
复制
返回值：
"AbA"
 */
public class MinWindowStr extends Sort {


  public static void main(String[] args) {
    String result = minWindow("xyzabc", "xab");
    System.out.println(result);

    result = minWindow20230801("xyzabc", "xza");
    System.out.println(result);
  }

  private static String minWindow20230801(String s, String t) {

    if (StringUtils.isEmpty(s) || StringUtils.isEmpty(t)) {
      return "";
    }
    int l =0, r = 0, d = Integer.MAX_VALUE, counter = t.length(), head =0;
    int mark[] = new int[128];
    for (int i = 0 ; i < t.length() ; i++) {
      mark[t.charAt(i)] ++;
    }
    for (; r<s.length(); r++) {
      if (mark[s.charAt(r)]-- > 0) {
        counter--;
      }
      while (counter == 0) {
        if (d > (r - l)) {
          d = r-l;
          head=l;
        }
        if (mark[s.charAt(l++)]-- ==0) {
          counter++;
        }
      }
    }

    return d == Integer.MAX_VALUE? "":s.substring(head, head+d+1);
  }

  //滑动窗口
    /**
     *
     * @param S string字符串
     * @param T string字符串
     * @return string字符串
     */
    public static String minWindow (String S, String T) {
        if(S.length() == 0 || T.length() == 0) return "" ;
        int minCount = Integer.MAX_VALUE ;
        int[] hash = new int[128] ;
        for(int i = 0 ; i < T.length() ; i ++) {
            hash[T.charAt(i)] -- ;
        }
        int minLen = Integer.MAX_VALUE ;//记录最小覆盖子串的长度
        int ri = 0 ;//记录最小覆盖子串的左边界
        int rj = 0 ;//记录最小覆盖子串的右边界
        int f = 0 ;//窗口右边界
        int s = 0 ;//窗口左边界
        while(f < S.length()) {//右边界向右移动
            hash[S.charAt(f)]++ ;//将当前右边界坐对对应的字符加入hash
            while(s <= f && check(hash)) {//如果已经覆盖了,则不断让左边界右移，寻找最短的满足要求的子串
                if(f - s + 1 < minLen) {//更新小覆盖子串的记录
                    minLen = f - s + 1 ;
                    ri = s ;
                    rj = f ;
                }
                hash[S.charAt(s)] -- ;//将左边界移除hash
                s ++ ;//左边界右移
            }
            f ++ ;//右边界右移
        }
        if(f - s + 1 > S.length()) {//如果右边界超出S时左边界都没动过，说明不存在覆盖子串
            return "" ;
        } else {//截取
            return S.substring(ri , rj + 1) ;
        }
    }
    public static boolean check(int hash[]) {
        for(int i = 0 ; i < hash.length ; i ++) {
            if(hash[i] < 0) {
                return false ;
            }
        }
        return true ;
    }
}
