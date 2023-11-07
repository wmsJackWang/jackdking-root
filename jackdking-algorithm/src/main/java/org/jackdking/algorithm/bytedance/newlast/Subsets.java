package org.jackdking.algorithm.bytedance.newlast;

import java.util.ArrayList;
import java.util.List;

/*
. 题目
题目链接：78. 子集 - 力扣（LeetCode）: https://leetcode.cn/problems/subsets/
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。

解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。

1 <= nums.length <= 10
-10 <= nums[i] <= 10
nums 中的所有元素 互不相同
————————————————
版权声明：本文为CSDN博主「零一魔法」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/m0_49270962/article/details/126110208
 */
public class Subsets {


    public List<List<Integer>> ret = new ArrayList<>();
    public List<Integer> tempList = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        // 这儿的 i 表示组成单个结果的元素个数
        // 不是表示索引，所以需要在 nums.length 后 +1
        // 从零开始包含空集
        for (int i = 0; i < nums.length + 1; i++) {
            backTracking(nums, 0, i);
        }
        return ret;
    }

    public void backTracking(int[] nums, int startIndex, int k) {
        if (k == 0) {
            ret.add(new ArrayList(tempList));
            return;
        }

        // k - 1 表示除了本层之外（即 - 1）仍需要的元素个数
        // i 至多遍历到 nums.length - (k - 1) 即可，之后的元素留给下一层递归
        for (int i = startIndex; i < nums.length - (k - 1); i++) {
            tempList.add(nums[i]);
            backTracking(nums, i + 1, k - 1);
            tempList.remove(tempList.size() - 1);
        }
    }
//————————————————
//    版权声明：本文为CSDN博主「零一魔法」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/m0_49270962/article/details/126110208
}
