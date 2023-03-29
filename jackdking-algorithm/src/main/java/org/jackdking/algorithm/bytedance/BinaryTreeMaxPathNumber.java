package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BinaryTreeMaxPathNumber
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 4:23 下午
 * @Version 2.0
 **/
/*
 *  二叉树中的最大路径和
 *
      二叉树里面的路径被定义为:从该树的任意节点出发，经过父=>子或者子=>父的连接，达到任意节点的序列。
      注意:
      1.同一个节点在一条二叉树路径里中最多出现一次
      2.一条路径至少包含一个节点，且不一定经过根节点

      给定一个二叉树的根节点root，请你计算它的最大路径和
 */
public class BinaryTreeMaxPathNumber extends Sort{

  public static void main(String[] args) {

    Integer[] arr = createArray(10, 9);
    printArray("原数组：", arr);
    Sort.TreeNode list = createBinaryTree(arr);
    maxPathNumber(list);
    System.out.println("result:" +maxValue);

    maxPathNumber20230322(list);
    System.out.println("result:" +maxValue);

    printArray("原数组：", arr);
    list = createBinaryTree(arr);
    maxPathNumber20230327(list);
    System.out.println("result:" +maxValue);

    printArray("原数组：", arr);
    list = createBinaryTree(arr);
    maxPathNumber20230328(list);
    System.out.println("result:" +maxValue);

  }

  private static int maxPathNumber20230328(TreeNode list) {

    if (list == null) {
      return 0;
    }
    int left = maxPathNumber20230328(list.left);
    int right = maxPathNumber20230328(list.right);

    int cur = list.val + Math.max(0, left) + Math.max(0, right);
    int res = list.val + Math.max(0, Math.max(left, right));
    maxValue = Math.max(maxValue, Math.max(cur, res));
    return res;
  }

  private static int maxPathNumber20230322(TreeNode list) {
    if (list == null) {
      return 0;
    }
    int left = maxPathNumber20230322(list.left);
    int right = maxPathNumber20230322(list.right);
    int cur =list.val + Math.max(Math.max(0, right), Math.max(0, left));
    int res = list.val + Math.max(Math.max(left, right), 0);
    maxValue = Math.max(res, Math.max(cur, res));
    return res;
  }

    private static int maxPathNumber20230327(TreeNode list) {

      if (list == null) {
          return 0;
      }
      int left = maxPathNumber20230327(list.left);
      int right = maxPathNumber20230327(list.right);
      int res = list.val + Math.max(Math.max(0, left), Math.max(0, right));
      int cur = list.val + Math.max(0, Math.max(0, left) + Math.max(0, right));
      maxValue = Math.max(maxValue, Math.max(res, cur));

      return res;
    }

    /*
   *     6
   *  7     7
   * 3
   */

  static int maxValue = Integer.MIN_VALUE;//全局最大值，二叉树不同位置的路径值计算比较。
  /*
   * 1. 缓存二叉树各位置路径的最大和。
   * 2. 左右节点都在的path，就是跟独立的path了，跟全局最大path比较得最大值。
   * 3. 左右节点没有都参与进来，则不是完成path，即将值往上传递上去。
   */
  private static int maxPathNumber(TreeNode list) {

      if (list == null) {
        return 0;
      }
      int leftVal = maxPathNumber(list.left);
      int rightVal = maxPathNumber(list.right);
      int cur = list.val + Math.max(0, leftVal) + Math.max(0, rightVal);
      int res = list.val + Math.max(0, Math.max(leftVal, rightVal));

      maxValue = Math.max(maxValue, Math.max(cur, res));
      return res;
  }


}
