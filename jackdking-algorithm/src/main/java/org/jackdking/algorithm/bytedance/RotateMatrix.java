package org.jackdking.algorithm.bytedance;
/*
 * 描述
有一个NxN整数矩阵，请编写一个算法，将矩阵顺时针旋转90度。

给定一个NxN的矩阵，和矩阵的阶数N,请返回旋转后的NxN矩阵。

数据范围：0 < n < 3000<n<300，矩阵中的值满足 0 \le val \le 10000≤val≤1000

要求：空间复杂度 O(N^2)O(N
2
 )，时间复杂度 O(N^2)O(N
2
 )
进阶：空间复杂度 O(1)O(1)，时间复杂度 O(N^2)O(N
2
 )
 *
 * 示例1
输入：
[[1,2,3],[4,5,6],[7,8,9]],3
复制
返回值：
[[7,4,1],[8,5,2],[9,6,3]]
 */
// https://www.nowcoder.com/practice/2e95333fbdd4451395066957e24909cc?tpId=196&tqId=37123&rp=1&ru=/exam/company&qru=/exam/company&sourceUrl=%2Fexam%2Fcompany&difficulty=undefined&judgeStatus=undefined&tags=&title=
public class RotateMatrix {

    public static void main(String[] args) {
        int[][] temp = {{7,4,1}, {8,5,2}, {9,6,3}};

        //空间n的平方
        int [][] result = rotateMatrix(temp);
        //挑战空间为1的损耗


    }

    private static int[][] rotateMatrix(int[][] temp) {
        int[][] result = new int[temp.length][temp.length];
        for (int i = 0 ; i< temp.length ; i ++) {
            for (int j = 0 ; j < temp[i].length ; j++) {
                result[j][temp.length-1-i] = temp[i][j];
            }
        }
        return result;
    }

}
