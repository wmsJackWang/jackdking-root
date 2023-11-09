package org.jackdking.algorithm.bytedance.newlast;

import com.alibaba.fastjson.JSON;
import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/*

给你一个 无重叠 的 ，按照区间起始端点排序的区间列表。

在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。

示例 1：

输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
输出：[[1,5],[6,9]]
1
2
示例 2：

输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
输出：[[1,2],[3,10],[12,16]]
解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
1
2
3
示例 3：

输入：intervals = [], newInterval = [5,7]
输出：[[5,7]]
1
2
代码实现
————————————————
版权声明：本文为CSDN博主「写代码的小包」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/bao_14440/article/details/132548294
 */
public class InsertArea extends Sort {

    public static void main(String[] args) {
        int [][] areas = new int[][]{{1,2}, {4,6}, {12, 14}, {21, 45}, {50, 60}};
        int [] newArea = new int[]{9, 44};
        int [][] result = insert20231108(areas, newArea);
        System.out.println("结果：" + JSON.toJSONString(result));
    }

    private static int[][] insert20231108(int[][] areas, int[] newArea) {
        int i = 0;
        List<int[]> result = new ArrayList<>();
        while (areas[i][1] < newArea[0] && i < areas.length) {
            result.add(areas[i]);
            i++;
        }
        int l = Math.min(newArea[0], areas[i][0]), r = 0;
        while (i < areas.length && areas[i][0] <= newArea[1] ) {
            r = Math.max(newArea[1], areas[i][1]);
            i  ++;
        }

        result.add(new int[]{l, r});
        while (i < areas.length) {
            result.add(areas[i]);
            i++;
        }

        return result.toArray(new int [result.size()][]);
    }


    public int[][] insert(int[][] intervals, int[] newInterval) {
        int i = 0;
        List<int[]> merged = new ArrayList<>();
        // 添加区间最大值小于 newINterval 开始位置的
        while(i < intervals.length && intervals[i][1] < newInterval[0]) {
            merged.add(intervals[i]);
            i ++;
        }
        // 合并重叠区间
        while(i < intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[i][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[i][1], newInterval[1]);
            i ++;
        }
        merged.add(newInterval);
        // 添加剩下的区间到 merged 列表中
        while (i < intervals.length) {
            merged.add(intervals[i]);
            i++;
        }
        // 转换列表为二维数组并返回结果
        return merged.toArray(new int[merged.size()][]);
    }
//————————————————
//    版权声明：本文为CSDN博主「写代码的小包」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/bao_14440/article/details/132548294

}
