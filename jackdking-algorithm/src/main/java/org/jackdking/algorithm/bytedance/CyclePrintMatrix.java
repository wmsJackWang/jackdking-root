package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

public class CyclePrintMatrix extends Sort {
    public static void main(String[] args) {
        int [][] matrix = new int[][]{{7,4,1,4}, {8,5,2,6}, {9,6,3,11}};
        List<Integer> printResult = cyclePrintMatrix(matrix);
        printListInteger("循环打印矩阵：", printResult);

        printResult = cyclePrintMatrix20231031(matrix);
        printListInteger("循环打印矩阵：", printResult);

    }

    private static List<Integer> cyclePrintMatrix20231031(int[][] matrix) {

        List<Integer> printResult = new ArrayList<>();
        int max = Math.min((matrix.length+1)/2, (matrix[0].length+1)/2) -1;
        int oneMax, twoMax, threeMax, fourMax, index;
        for (int i = 0; i <= max ; i++) {
            oneMax = matrix[0].length-1-i;
            twoMax = matrix.length -1 -i;
            threeMax = matrix[0].length -1-i-1;
            fourMax = matrix.length -1-i-1;

            index = i;
            while (index <= oneMax) {
                printResult.add(matrix[i][index++]);
            }

            index = i+1;
            while (index <= twoMax) {
                printResult.add(matrix[index++][matrix[0].length-1-i]);
            }

            index = matrix[0].length-1-i-1;
            while (index >= i && matrix.length-1-i > i) {
                printResult.add(matrix[matrix.length-1-i][index--]);
            }

            index = fourMax;
            while (index >= i+1) {
                printResult.add(matrix[i][index--]);
            }
        }
        return printResult;

    }

    private static List<Integer> cyclePrintMatrix(int[][] matrix) {
        List<Integer> printResult = new ArrayList<>();

        int max = Math.min((matrix.length + 1)/2, (matrix[0].length + 1)/2);

        for (int i = 0 ; i <= max-1 ; i++) {

            for (int k = i ; k <= matrix[0].length - 1- i; k++){
                printResult.add(matrix[i][k]);
            }

            for (int k = i+1; k <= matrix.length- 1 -i ; k ++){
                printResult.add(matrix[k][matrix[0].length-1-i]);
            }
            if (matrix[0].length - 1 - i-1 > i) {
                for (int k = matrix[0].length - 1 - i-1; k >= i; k--){
                    printResult.add(matrix[matrix.length-i-1][k]);
                }
            }
            if (matrix.length - 1 - i-1 > i) {
                for (int k = matrix.length - 1 - i-1; k >= i+1; k--){
                    printResult.add(matrix[k][i]);
                }
            }
        }
        return printResult;
    }
}
