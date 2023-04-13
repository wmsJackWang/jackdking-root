package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/*
 * 描述
    现在有一个只包含数字的字符串，将该字符串转化成IP地址的形式，返回所有可能的情况。
    例如：
    给出的字符串为"25525522135",
    返回["255.255.22.135", "255.255.221.35"]. (顺序没有关系)

    数据范围：字符串长度 0 \le n \le 120≤n≤12
    要求：空间复杂度 O(n!)O(n!),时间复杂度 O(n!)O(n!)

    注意：ip地址是由四段数字组成的数字序列，格式如 "x.x.x.x"，其中 x 的范围应当是 [0,255]。

    示例1
    输入：
    "25525522135"
    复制
    返回值：
    ["255.255.22.135","255.255.221.35"]
    复制
    示例2
    输入：
    "1111"
    复制
    返回值：
    ["1.1.1.1"]
 */
//https://www.nowcoder.com/practice/ce73540d47374dbe85b3125f57727e1e?tpId=196&tqId=37059&rp=1&ru=/exam/intelligent&qru=/exam/intelligent&sourceUrl=%2Fexam%2Fintelligent%3FfromPut%3Dad_baidu_sem_wushuang_hangyetiku230216%26plan_id%3D273391570%26unit_id%3D7971864692%26idea_id%3D70478809472%26keyword%3D%25E8%2587%25AA%25E5%258A%25A8%25E5%258C%2596%25E9%259D%25A2%25E8%25AF%2595%25E9%25A2%2598%25E7%259B%25AE%26bd_vid%3D8346193917978018114&difficulty=undefined&judgeStatus=undefined&tags=&title=
public class RestoreIpAddresses extends Sort {
  public static void main(String[] args) {

      String str = "25525522135";
      List<String> resultList = restoreIpAddresses(str);
      printListStr(resultList);

      resultList = restoreIpAddresses20230413(str);

  }

  static ArrayList<String> ipList2 = new ArrayList<>();
  private static List<String> restoreIpAddresses20230413(String str) {
      if (str == null || str.length()<4) {
        return ipList2;
      }
      backTrack20230413(str, 0, 3);
      return ipList2;
  }

  private static void backTrack20230413(String str, int start, int num) {

    if (num == 0) {
      String[] spliterStr = str.split("\\.");
      if (spliterStr.length < 4) {
        return;
      }

    }

  }

  static ArrayList<String> ipList = new ArrayList<>();
    public static ArrayList<String> restoreIpAddresses (String s) {
        // write code here

        if (s==null || s.length() ==0){
            return ipList;
        }
        backTrack(s, 0, 3);
        return ipList;

    }

    private static void backTrack(String s, int start, int num) {

        if (num == 0){
            String [] splitStr = s.split("\\.");
            if (splitStr.length < 4){
                return;
            }
            for (String str : splitStr) {
                if (str.length()>1 && str.charAt(0) == '0') return;
                if (Integer.valueOf(str) > 255) return;
            }
            ipList.add(s);
            return;
        }

        if (start >= s.length()) return;
        int len = s.length();
        backTrack(s.substring(0, start+1) + "." + s.substring(start+1, len), start +2, num -1);
        if (start < len - 2) {
            backTrack(s.substring(0, start +2) + "." + s.substring(start+2, len), start +3, num -1);
        }
        if (start< len -3){
            backTrack(s.substring(0, start +3) + "." + s.substring(start+3, len), start + 4, num -1);
        }
    }
}
