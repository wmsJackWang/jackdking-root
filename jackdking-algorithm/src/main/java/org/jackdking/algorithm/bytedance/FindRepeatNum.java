package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

public class FindRepeatNum extends Sort {

    public static void main(String[] args) {

        int [] arr = {1, 3, 0, 2, 3};
        int result = findRepeatNum(arr);
        System.out.println("任意重复的数字：" + (result == -1? "无":result));
    }

    private static int findRepeatNum(int[] arr) {
        int k = 0;

        for (int i = 0 ; i < arr.length ;) {
            if (arr[i] == i) {
                i++;
                continue;
            }
            if (arr[i]== arr[arr[i]]) {
                return arr[i];
            }

            k = arr[arr[i]];
            arr[arr[i]] = arr[i];
            arr[i] = k;
        }
        return -1;
    }
}
