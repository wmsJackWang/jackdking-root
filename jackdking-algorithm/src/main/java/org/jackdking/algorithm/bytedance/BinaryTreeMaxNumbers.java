package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.util.Stack;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BinaryTreeMaxNumbers
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 3:39 下午
 * @Version 2.0
 **/
public class BinaryTreeMaxNumbers extends Sort {
  public static void main(String[] args) {

    Integer[] arr = createArray(4, 9);
    printArray("原数组：", arr);
    TreeNode list = createBinaryTree(arr);
    int result = maxNumbers(list);
    System.out.println("result:" +result);
  }

  private static int maxNumbers(TreeNode list) {
    if (list == null) {
      return 0;
    }
    Stack<TreeNode> nodeStack = new Stack<>();
    Stack<Integer> valStack = new Stack<>();

    nodeStack.push(list);
    valStack.push(list.val);
    TreeNode p;
    Integer val = 0, maxNum = 0;
    while (!nodeStack.isEmpty()) {
      p = nodeStack.pop();
      val = valStack.pop();

      if (p.left == null && p.right == null) {
          if (val > maxNum) {
            maxNum = val;
          }
      }else {
        if (p.right != null) {
          nodeStack.push(p.right);
          valStack.push(p.right.val + val * 10);
        }
        if (p.left != null) {
          nodeStack.push(p.left);
          valStack.push(p.left.val + val * 10);
        }
      }
    }
    return maxNum;
  }
}
