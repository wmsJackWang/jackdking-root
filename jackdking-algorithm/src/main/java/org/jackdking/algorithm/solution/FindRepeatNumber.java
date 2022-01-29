package org.jackdking.algorithm.solution;

public class FindRepeatNumber {

    public static void main(String[] args) {
        int [] array = {6,3,2,0,2,5,0};
        int [] result = new int[1];

        boolean isTure = duplicate(array, array.length, result);

        System.out.println("result: " + result[0] + "," + isTure);
    }

    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers == null || length == 0) {
            return false;
        }

        int k = 0;
        for(int i = 0 ; i < length ; ++i){
            if(numbers[i] > k) {
                k = numbers[i];
            }
        }

        if(k<=0) {
            return false;
        }
        //创建标记数组
        int[] keys = new int[k+1];

        //初始化标记数组
        for(int i = 0 ; i <= k ; ++i){
            keys[i] = 0;
        }
//        这是找到所有的重复数字 逻辑代码
//        //对原始数组全部数据打标
//        for(int i = 0 ; i < length ; ++i){
//            keys[numbers[i]] += 1;
//        }
//
//        for(int i = 0 ; i <= k ; ++i){
//            if(keys[i] > 1){
//                duplication[0] = i;
//                return true;
//            }
//        }

        //对原始数组 由左到右部分打标
        for(int i = 0 ; i < length ; ++i){
            if (keys[numbers[i]] == 1) {
                duplication[0] = numbers[i];
                return true;
            }
            keys[numbers[i]] += 1;
        }

    //只获取第一个重复数字


        return false;

    }

}
