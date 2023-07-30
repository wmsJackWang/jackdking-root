package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.HashMap;

/*
 * 描述
给定一个无重复元素的整数数组nums，请你找出其中没有出现的最小的正整数

进阶： 空间复杂度 O(1)O(1)，时间复杂度 O(n)O(n)

数据范围:
-2^{31}\le nums[i] \le 2^{31}-1−2
31
 ≤nums[i]≤2
31
 −1
0\le len(nums)\le5*10^50≤len(nums)≤5∗10
5

示例1
输入：
[1,0,2]
复制
返回值：
3
复制
示例2
输入：
[-2,3,4,1,5]
复制
返回值：
2
复制
示例3
输入：
[4,5,6,8,9]
复制
返回值：
1
 */
public class MinNumberDisappeared extends Sort {

  public static void main(String[] args) {

    Integer temp[] = new Integer[]{3,2,4,1,5,8,7,9,10,23};

    Integer arr[] = createArray(10, 30);
    arr = new Integer[]{3,2,4,1,5,8,7,9,10,23};

    printArray("arr", arr);
    int result = findMinNumberDisappeared(arr);
    System.out.println("\nresult:"+result);

    arr = createArray(10, 30);
    arr = new Integer[]{3,2,4,1,5,8,7,9,10,23,6,12};

    printArray("arr", arr);
    result = findMinNumberDisappeared20230718(arr);
    System.out.println("\nresult:"+result);
  }

  private static int findMinNumberDisappeared20230718(Integer[] arr) {
    int n = arr.length;
    for (int i = 0; i < arr.length ; i++) {
      while (arr[i]>0 && arr[i] <=n && arr[arr[i] -1] != arr[i]) {
        swap(arr, i, arr[i] -1);
      }
    }
    for (int i = 0; i < n; i++){
      if (arr[i] != i+1) {
        return i+1;
      }
    }
    return n+1;
  }

  private static void swap(Integer[] arr, int i, int j) {
      int k;
      k = arr[i];
      arr[i] = arr[j];
      arr[j] = k;
  }

  private static int findMinNumberDisappeared(Integer[] arr) {
    int index = 0, res = 0;
    HashMap<String, Integer> map = new HashMap();
    for (Integer e : arr) {
      map.put(String.valueOf(e), e);
    }
    for (int i = 1; i < arr.length; i++) {
      if (map.get(String.valueOf(i))!=null) {
        continue;
      } else {
        res = i;
        break;
      }
    }
    return res;
  }


  /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public int minNumberDisappeared (int[] nums) {
        // write code here
        HashMap<Integer,Integer> maps=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            maps.put(nums[i], maps.getOrDefault(nums[i], 0)+1);
        }
        int index=1;
        int res=1;
        while(true){
            if(!maps.containsKey(index)){
                res=index;
                break;
            }else{
                index++;
            }
        }
        return res;
    }
}
