package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

public class FirstNoRepeatingChar extends Sort {

    public static void main(String[] args) {

        int resultIndex = FirstNoRepeatingCharIndex("hello_herry");
        System.out.println(resultIndex);
    }

    private static int FirstNoRepeatingCharIndex(String s) {

        int mark[] = new int[128];
        for (int i = 0;i < s.length() ; i++) {
            mark[s.charAt(i)] ++;
        }

        for (int i = 0 ; i < s.length() ; i ++) {
            if (mark[s.charAt(i)] == 1) {
                return i;
            }
        }
        return -1;
    }

}
