package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

public class FindKNumberQuickSort extends Sort {
    public static void main(String[] args) {
        int [] arr = new int[]{2,3,5,7,8,0,11,33,56,4,56,7};
        printArray("原数组", arr);
        int val = findKNumber(arr, 0, arr.length-1, 10);
        System.out.println("第k个数:"+val);
    }

    private static int findKNumber(int[] arr, int start, int end, int k) {
        if (start > end){
            return -1;
        }
        int val = 0;
        int p,q;
        for (p = start, q=start; q < end ; q++) {
           if (arr[q] < arr[end]) {
               if (p!=q) {
                   val = arr[p];
                   arr[p] = arr[q];
                   arr[q] = val;
               }
               p++;
           }
        }
        if (p!= end){
            val = arr[p];
            arr[p] = arr[end];
            arr[end] = val;
        }

        if (p==k-1){
            return arr[p];
        } else if (p > k-1) {
            return findKNumber(arr, start, p-1, k);
        } else {
            return findKNumber(arr, p+1, end, k);
        }

    }

}
