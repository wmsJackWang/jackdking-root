package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

public class FindRepeatAllNum extends Sort {
    public static void main(String[] args) {

        int [] arr = {1, 2, 0, 3, 3, 2, 2};
        List<Integer> allRepeatNum = new ArrayList<>();
        int result = findRepeatAllNum(arr, allRepeatNum);
        printListInteger("所有重复的数字:", allRepeatNum);

    }

    private static int findRepeatAllNum(int[] arr, List<Integer> allRepeatNum) {

        int k = 0, index = -1 ;
        for (int i = 0; i < arr.length ;) {
            if (arr[i] == i || arr[i] == -1) {
                i++;
                continue;
            }
            if (arr[i] == arr[arr[i]]) {
                allRepeatNum.add(arr[i]);
                arr[i] = index;
                continue;
            }

            k = arr[arr[i]];
            arr[arr[i]] = arr[i];
            arr[i] = k;
        }
        return -1;

    }

}
