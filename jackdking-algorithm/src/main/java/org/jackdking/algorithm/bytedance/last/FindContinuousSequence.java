package org.jackdking.algorithm.bytedance.last;

import java.util.ArrayList;

/*
 * 题目描述
小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
输出描述:
输出所有和为S的连续正数序列。序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
思路很简单，就是控制好两个端，然后就是判断
 */
public class FindContinuousSequence {
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        if (sum <= 0) return null;
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int left = 1;
        int right = 2;
        int mid = sum/2;
        int curSum = left + right;
        while(left > 0 && right <= mid+1) {
            if(curSum < sum) {
                curSum += (++right);
            } else if(curSum > sum) {
                curSum -= left;
                left++;
            } else {
                result.add(formatList(left,right));
                curSum -= left;
                left++;
            }
        }
        return result;
    }

    public ArrayList<Integer> formatList(int left, int right) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = left; i <= right; i++) {
            list.add(i);
        }
        return list;
    }
}
