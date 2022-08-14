package org.jackdking.algorithm.kquerstion;

import java.util.ArrayList;

public class Solution {
    static ArrayList<Integer> result = new ArrayList<Integer>();

    public static void main(String[] args) {
        GetLeastNumbers_Solution(new int[]{4,5,1,6,2,7,3,8}, 4);
        System.out.println(result);
    }

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        qSort(input, k, 0, input.length-1);
        return result;
    }

    public static void qSort(int [] input, int k, int x, int y){

        if(x > y){
            return;
        }
//        if(x==y && k ==1) {
//            result.add(input[x]);
//        }

        int tmp , p1=x, p2;
        for(p2 = x ; p2< y; p2++) {
            if(input[y] > input[p2]) {
                if(p2!=p1) {
                    tmp = input[p2];
                    input[p2] = input[p1];
                    input[p1] = tmp;
                }
                p1++;
            }
        }
        if(p1!=y) {
            tmp = input[y];
            input[y] = input[p1];
            input[p1] = tmp;
        }
        if(k >= p1-x+1) {
            for(int i = x; i <= p1 ; i++ ){
                result.add(input[i]);
            }

            qSort(input, k-(p1-x+1), p1+1, y);

        }else {
            qSort(input, k, x, p1 -1);
        }

    }
}