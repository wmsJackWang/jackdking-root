package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.*;

/*
 * 描述
现在有一个没有重复元素的整数集合S，求S的所有子集
注意：
你给出的子集中的元素必须按升序排列
给出的解集中不能出现重复的元素

数据范围：1 \le n \le 51≤n≤5，集合中的任意元素满足 |val| \le 10∣val∣≤10
要求：空间复杂度 O(n!)O(n!)，时间复杂度 O(n!)O(n!)
示例1
输入：
[1,2,3]
复制
返回值：
[[],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3]]
复制
示例2
输入：
[]
复制
返回值：
[]
 */
public class Subsets extends Sort {

    static ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    /**
     * 该递归函数完成后，result中是： 在 arr 中 选择k个数字的全部组合(无重复)。
     * 例如arr[]={1,2,3} 那么当backtracking(2,0,new ArrayList(),arr) 调用返回后  result={{1,2},{1,3},{2,3}};
     *
     * @param k     选择k个数字的组合
     * @param start 从数组的 start位置 开始
     * @param list  一个组合
     * @param arr   原数组
     */
    public static void backtracking(int k, int start, List<Integer> list, int[] arr) {
        if (k < 0)
            return;
        else if (k == 0) {
            //k==0表示已经找到了k个数字的组合，这时候加入全局result中
            result.add(new ArrayList(list));
        } else {
            for (int i = start; i < arr.length; i++) {
                list.add(arr[i]);
                //上一行代码选择了一个数字 ， 接着对于剩余数字 做组合。 k-1 表示接下来少选一个数，i+1表示 从数组的i+1开始
                backtracking(k - 1, i + 1, list, arr);
                // 去掉 arr[i] , 下一轮循环跳过这个数。 数组内每一个元素 都有两种状态：选或者不选。
                list.remove(list.size() - 1);
            }
        }
    }

    /**
     * 循环执行 backtracking 选择有 0 ,1 ,2, 3.....arr.length 个元素的组合 ，就构成了arr的所有子集（无重复）
     */
    public static ArrayList<ArrayList<Integer>> subsets(int[] S) {
        for (int i = 0; i <= S.length; i++) {
            //选择i个数的组合
            backtracking(i, 0, new ArrayList<>(), S);
        }
        return result;
    }







    boolean[] visited;
    ArrayList<ArrayList<Integer>> ll = new ArrayList<>();
    Set<String> set = new HashSet<>();
    public ArrayList<ArrayList<Integer>> subsetsV2(int[] S) {
        visited = new boolean[S.length];
        visit(0, S, visited);

        return ll;
    }

    private void visit(int n, int[] S, boolean[] visited){
        if (n>=S.length){
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i=0; i<visited.length; ++i){
                if (visited[i]){
                    list.add(S[i]);
                }
            }
            Collections.sort(list);
            StringBuilder sb = new StringBuilder();
            for (int val : list){
                sb.append(val);
            }

            if (set.add(sb.toString())){
                ll.add(list);
            }
            return;
        }

        visit(n+1, S, visited);
        visited[n]=true;
        visit(n+1, S, visited);
        visited[n]=false;
    }





}
