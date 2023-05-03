package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName BinarySymmetry
 * @Description TODO
 * @Author jackdking
 * @Date 21/03/2023 1:02 下午
 * @Version 2.0
 **/
//对称二叉树检测
// https://www.nowcoder.com/practice/ff05d44dfdb04e1d83bdbdab320efbcb?tpId=196&tqId=37058&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Fcompany%3D665%26page%3D1%26pageSize%3D50%26search%3D%26tab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D196&difficulty=undefined&judgeStatus=undefined&tags=&title=
public class BinarySymmetry extends Sort {
  public static void main(String[] args) {
    Integer[] arr = new Integer[]{1,2,2,3,4,4,3};
//    Integer[] arr = new Integer[]{1};
//    Integer[] arr = new Integer[]{1,2};
//    Integer[] arr = createArray(10, 20);
    TreeNode tree = createBinaryTree(arr);
    boolean result = symmetryBinary(tree);
    System.out.println("是否为对称二叉树：" + result);

    //求左右子树的坐标以及其值的列表后，再比较是否相等。
    result = symmetryBinary20230321(tree);
    System.out.println("是否为对称二叉树：" + result);

    //求左右子树的坐标以及其值的列表后，再比较是否相等。
    result = symmetryBinary20230322(tree);
    System.out.println("是否为对称二叉树：" + result);

    //求左右子树的坐标以及其值的列表后，再比较是否相等。
    result = symmetryBinary20230329(tree);
    System.out.println("是否为对称二叉树：" + result);

    //求左右子树的坐标以及其值的列表后，再比较是否相等。
    result = symmetryBinary20230331(tree);
    System.out.println("是否为对称二叉树：" + result);

    //求左右子树的坐标以及其值的列表后，再比较是否相等。
    result = symmetryBinary20230417(tree);
    System.out.println("是否为对称二叉树：" + result);

  }

  private static boolean symmetryBinary20230417(TreeNode tree) {
    if (tree==null) {
      return true;
    }
    Stack<TreeNode> stack = new Stack<>();
    TreeNode left = tree.left, right = tree.right;
    stack.push(left);
    stack.push(right);
    while (!stack.isEmpty()) {
      left = stack.pop();
      right = stack.pop();
      if (left != null ^ right !=null){
        return false;
      }
      if (left != null && right != null && left.val != right.val){
        return false;
      }
      if (left == null && right ==null) {
        continue;
      }
      stack.push(left.left);
      stack.push(right.right);
      stack.push(right.left);
      stack.push(left.right);
    }
    return true;
  }

  private static boolean symmetryBinary20230331(TreeNode tree) {
    if (tree == null) {
      return true;
    }
    List<TreeNode> nodeList = new ArrayList<>();
    nodeList.add(tree.left);
    nodeList.add(tree.right);
    TreeNode left, right;
    while (!nodeList.isEmpty()) {
      left = nodeList.get(0);
      nodeList.remove(0);
      right = nodeList.get(0);
      nodeList.remove(0);

      if (left == null && right == null) {
        continue;
      }
      if (left != null ^ right != null) {
        return false;
      }
      if (left.val != right.val) {
        return false;
      }
      nodeList.add(left.left);
      nodeList.add(right.right);
      nodeList.add(left.right);
      nodeList.add(right.left);
    }
    return true;
  }

  private static boolean symmetryBinary20230329(TreeNode tree) {
    if (tree == null) {
      return true;
    }

    Stack<TreeNode> nodeStack = new Stack<>();
    TreeNode left, right;
    nodeStack.push(tree.left);
    nodeStack.push(tree.right);
    while (!nodeStack.isEmpty()) {
      left = nodeStack.pop();
      right = nodeStack.pop();
      if (left == null && right == null) {
        continue;
      }
      if (left != null ^ right != null) {
        return false;
      }

      if (left.val != right.val) {
        return false;
      }
      nodeStack.push(left.left);
      nodeStack.push(right.right);
      nodeStack.push(left.right);
      nodeStack.push(right.left);
    }
    return true;
  }

  private static boolean symmetryBinary20230322(TreeNode tree) {

    if (tree == null) {
      return true;
    }
    ArrayList<TreeNode> queue = new ArrayList<>();
    queue.add(tree.left);
    queue.add(tree.right);

    TreeNode left, right;
    while (!queue.isEmpty()){
      left = queue.get(0);
      queue.remove(0);
      right = queue.get(0);
      queue.remove(0);
      if (left == null && right == null) {
        continue;
      }
      if (left!=null ^ right != null) {
        return false;
      }
      if (left != null && right != null && left.val != right.val){
        return false;
      }
      queue.add(left.left);
      queue.add(right.right);
      queue.add(left.right);
      queue.add(right.left);
    }
    return true;
  }

  private static boolean symmetryBinary20230321(TreeNode tree) {
    if (tree == null) {
      return true;
    }
    Stack<TreeNode> nodeStack = new Stack<>();
    nodeStack.push(tree.left);
    nodeStack.push(tree.right);
    TreeNode left, right;
    while (!nodeStack.isEmpty()) {
      right = nodeStack.pop();
      left = nodeStack.pop();
      if (right == null && left == null) {
        continue;
      }
      if (left !=null ^ right !=null || left.val != right.val) {
        return false;
      }
      nodeStack.push(left.left);
      nodeStack.push(right.right);
      nodeStack.push(left.right);
      nodeStack.push(right.left);
    }
    return true;
  }

  private static boolean symmetryBinary(TreeNode tree) {
    if (tree == null){
      return true;
    }
    return checkDuiChengBinary(tree.left, tree.right);
  }

  private static boolean checkDuiChengBinary(TreeNode leftTree, TreeNode rightTree) {
    if (leftTree == null && rightTree == null) {
      return true;
    }

    if (leftTree != null ^ rightTree != null) {
      return false;
    }
    if (leftTree.val != rightTree.val) {
      return false;
    }
    boolean leftRes = checkDuiChengBinary(leftTree.left, rightTree.right);
    boolean rightRes = checkDuiChengBinary(leftTree.right, rightTree.left);
    return leftRes && rightRes;
  }
}
