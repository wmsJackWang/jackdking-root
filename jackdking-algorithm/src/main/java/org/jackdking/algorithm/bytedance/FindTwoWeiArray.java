package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/*
 * 描述 二维数组中的查找
在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
[
[1,2,8,9],
[2,4,9,12],
[4,7,10,13],
[6,8,11,15]
]
给定 target = 7，返回 true。

给定 target = 3，返回 false。

数据范围：矩阵的长宽满足 0 \le n,m \le 5000≤n,m≤500 ， 矩阵中的值满足 0 \le val \le 10^90≤val≤10
9

进阶：空间复杂度 O(1)O(1) ，时间复杂度 O(n+m)O(n+m)
示例1
输入：
7,[[1,2,8,9],[2,4,9,12],[4,7,10,13],[6,8,11,15]]
复制
返回值：
true
复制
说明：
存在7，返回true
示例2
输入：
1,[[2]]
复制
返回值：
false
复制
示例3
输入：
3,[[1,2,8,9],[2,4,9,12],[4,7,10,13],[6,8,11,15]]
复制
返回值：
false
复制
说明：
不存在3，返回false
 */
/*
 * 区域型思维
 *
 */
public class FindTwoWeiArray extends Sort {

  public static void main(String[] args) {
    int [][] arr = new int[][]{{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
    boolean result = findValueInTwoWeiArray(0, arr);
    System.out.println("数组是否存在："+result);

  }

  private static boolean findValueInTwoWeiArray(int value, int[][] arr) {
    int row = 0, col = arr[0].length-1;
    while (row < arr.length && col >= 0){
      if (value > arr[row][col]){
        row++;
      } else if (value < arr[row][col]) {
        col--;
      } else {
        return true;
      }
    }
    return false;

  }

  public boolean Find(int target, int [][] array) {
            for(int i=0;i<array.length;i++){
                for(int j=0;j<array[0].length;j++){
                    if(array[i][j] == target){
                        return true;
                    }
                }
            }
            return false;
        }

    public boolean FindV2(int target, int [][] array) {
        int rows = array.length;
        if(rows == 0){
            return false;
        }
        int cols = array[0].length;
        if(cols == 0){
            return false;
        }
        // 左下
        int row = rows-1;
        int col = 0;
        while(row>=0 && col<cols){
            if(array[row][col] < target){
                col++;
            }else if(array[row][col] > target){
                row--;
            }else{
                return true;
            }
        }
        return false;
    }

}
