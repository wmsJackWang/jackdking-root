package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import javax.xml.validation.Validator;
import java.util.Stack;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BinaryTreeSumNumbers
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 2:23 下午
 * @Version 2.0
 **/
public class BinaryTreeSumNumbers extends Sort {
  public static void main(String[] args) {

    Integer[] arr = createArray(4, 9);
    printArray("原数组：", arr);
    TreeNode list = createBinaryTree(arr);
    /*
     * 1. 深度遍历，栈结构解法，先右后左；
     * 2. 每层节点值乘以10 再加上子节点值得到新的数。
     * 3. 对每个左右节点都为空的节点，其值加入到总数。
     */
    int result = sumNumbers(list);
    System.out.println("result:" +result);


    result = sumNumbers20230703(list);
    System.out.println("result:" +result);


  }

  private static int sumNumbers20230703(TreeNode list) {
    if (list==null) {
      return 0;
    }
    TreeNode p, left, right;
    Integer val, sum=0;
    Stack<TreeNode> nodeStack = new Stack<>();
    Stack<Integer> valStack = new Stack<>();

    nodeStack.push(list);
    valStack.push(list.val);

    while (!nodeStack.isEmpty()) {

      val = valStack.pop();
      p = nodeStack.pop();
      left = p.left;
      right = p.right;

      if (left ==null && right ==null) {
        sum +=val;
      }
      if (left != null){
        nodeStack.push(left);
        valStack.push(val*10 + left.val);
      }
      if(right != null) {
        nodeStack.push(right);
        valStack.push(val *10+right.val);
      }

    }
    return sum;

  }

  /*
   *      1
   * 8      0
   * 7
   */

  private static int sumNumbers(TreeNode list) {
    if (list == null) {
      return 0;
    }
    Stack<TreeNode> nodeStack = new Stack<>();
    Stack<Integer> valStack = new Stack<>();
    int sum = 0;
    nodeStack.push(list);
    valStack.push(list.val);
    TreeNode p = nodeStack.pop();
    int val = valStack.pop();
    while (p!=null){
      if (p.left == null && p.right == null) {
        sum += val;
      } else {
        if (p.right != null) {
          nodeStack.push(p.right);
          valStack.push(val*10+p.right.val);
        }
        if (p.left != null) {
          nodeStack.push(p.left);
          valStack.push(val*10+p.left.val);
        }
      }
      if (nodeStack.isEmpty()){
        break;
      }
      p = nodeStack.pop();
      val = valStack.pop();
    }
    return sum;

  }
}
