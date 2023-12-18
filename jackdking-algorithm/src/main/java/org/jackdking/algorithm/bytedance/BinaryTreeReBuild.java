package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ReBuildBinaryTree
 * @Description TODO
 * @Author jackdking
 * @Date 20/03/2023 6:20 下午
 * @Version 2.0
 **/
/*
 * 无重复元素是关键，有重复元素，则该解法失效
 * https://www.nowcoder.com/practice/8a19cbe657394eeaac2f6ea9b0f6fcf6?tpId=196&tqId=37109&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Fcompany%3D665%26page%3D1%26pageSize%3D50%26search%3D%26tab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D196&difficulty=undefined&judgeStatus=undefined&tags=&title=
 */
public class BinaryTreeReBuild extends Sort {

  public static void main(String[] args) {
    int[] pre = new int[]{0,1,3,4,2,5,6};
    int[] mid = new int[]{3,1,4,0,5,2,6};

    //根据前序、中序来重建数组。
    TreeNode tree = rebuildBinaryTree(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

    pre = new int[]{0,1,3,4,2,5,6};
    mid = new int[]{3,1,4,0,5,2,6};
    tree = rebuildBinaryTree20230323(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

    tree = rebuildBinaryTree20230331(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

    tree = rebuildBinaryTree20230416(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);


    tree = rebuildBinaryTree20231031(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);
    //总结： 总之核心点是划分出左右子树。而中序是关键，并且要求节点的值不能重复，否则无法区分。

    tree = rebuildBinaryTree20231102(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);
    //总结：循环体内++,与条件内++是有很大区别的，条件内++永远会比条件内++多执行一次。


    tree = rebuildBinaryTree20231105(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

    tree = rebuildBinaryTree20231107(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

    tree = rebuildBinaryTree20231107V1(pre, mid, 0, pre.length-1, 0, mid.length-1);
    System.out.println();
    System.out.println(tree.left.val);
    prePrintTreeNode(tree);
    System.out.println();
    midPrintTreeNode(tree);

  }

  private static TreeNode rebuildBinaryTree20231107V1(int[] pre, int[] mid, int preS, int preE, int midS, int midE) {

    if (preS > preE || midS > midE) {
      return null;
    }

    int index = midS, length = 0;
    while (pre[preS] != mid[index]) {
      index ++;
    }
    length = index - midS;
    TreeNode node = new TreeNode(pre[preS]);
    node.left = rebuildBinaryTree20231107V1(pre, mid, preS+1, preS+length, midS, index -1);
    node.right = rebuildBinaryTree20231107V1(pre, mid, preS+length+1, preE, index+1, midE);
    return node;
  }

  private static TreeNode rebuildBinaryTree20231107(int[] pre, int[] mid, int preS, int preE, int midS, int midE) {

    if (preS > preE || midS > midE) {
      return null;
    }
    TreeNode node = new TreeNode(pre[preS]);
    int index = midS, w = 0;
    while (pre[preS]!= mid[index]) {
      index++;
    }
    w = index - midS;//这个index的值是不会参与到下一层计算的。
    node.left = rebuildBinaryTree20231107(pre, mid, preS+1, preS + w, midS, index-1);
    node.right = rebuildBinaryTree20231107(pre, mid, preS+w+1, preE, index+1, midE);
    return node;

  }

  private static TreeNode rebuildBinaryTree20231105(int[] pre, int[] mid, int pre_start, int pre_end, int mid_start, int mid_end) {

    if (pre_start > pre_end || mid_start > mid_end) {
      return null;
    }

    TreeNode node = new TreeNode(pre[pre_start]);
    int index = mid_start;
    while (mid[index] != pre[pre_start]) {
      index++;
    }

    int length = index - mid_start;
    node.left = rebuildBinaryTree20231105(pre, mid, pre_start +1, pre_start+length, mid_start, index-1);
    node.right = rebuildBinaryTree20231105(pre, mid, pre_start+length+1, pre_end, index+1, mid_end);

    return node;
  }

  private static TreeNode rebuildBinaryTree20231102(int[] pre, int[] mid, int preS, int preE, int midS, int midE) {
    if (preS > preE || midS > midE) {
      return null;
    }
    TreeNode node = new TreeNode(pre[preS]);
    int index = midS;
    while (mid[index]!=pre[preS]){
      index++;
    }
    int lenght = index - midS;

    node.left = rebuildBinaryTree20231102(pre, mid, preS+1, preS + lenght, midS, index-1);
    node.right = rebuildBinaryTree20231102(pre, mid, preS+lenght+1, preE, index+1, midE);

    return node;
  }

  private static TreeNode rebuildBinaryTree20231031(int[] pre, int[] mid, int midStart, int midEnd, int preStart, int preEnd) {
    if (midStart >midEnd || preStart>preEnd) {
      return null;
    }
    TreeNode treeNode = new TreeNode(pre[preStart]);
    int headIndex = midStart;
    while (pre[preStart] != mid[headIndex]) {
      headIndex++;
    }
    int leftLength = headIndex - midStart;
    treeNode.left = rebuildBinaryTree20231031(pre, mid, midStart, headIndex-1, preStart+1, preStart+leftLength);
    treeNode.right = rebuildBinaryTree20231031(pre, mid, headIndex +1, midEnd, preStart+leftLength+1, preEnd);
    return treeNode;
  }

  private static TreeNode rebuildBinaryTree20230416(int[] pre, int[] mid, int preS, int preE, int midS, int midE) {

    if (preS> preE) {
      return null;
    }
    int i = 0;
    for (;i <= midE; i++){
      if (pre[preS] == mid[i]) {
        break;
      }
    }

    TreeNode root = new TreeNode(pre[preS]);
    root.left = rebuildBinaryTree20230416(pre, mid, preS + 1, preS + i - midS, midS, i-1);
    root.right = rebuildBinaryTree20230416(pre, mid, preS+i-midS +1, preE, i +1, midE);
    return root;
  }
  public static TreeNode rebuildBinaryTree(int[] pre, int[] mid, int preStart, int preEnd, int midStart, int midEnd) {

    if (preStart>preEnd) {
      return null;
    }
    int k = midStart;
    TreeNode root = new TreeNode(pre[preStart]);
    while (k <= midEnd) {
      if (mid[k] == pre[preStart]) {
        break;
      }
      k ++;
    }
    root.left = rebuildBinaryTree(pre, mid, preStart+1, preStart + (k - midStart), midStart, k - 1);
    root.right = rebuildBinaryTree(pre, mid, preStart+(k-midStart)+1, preEnd, k +1, midEnd);
    return root;
  }

  public static void prePrintTreeNode(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.print(root.val+ " ");
    prePrintTreeNode(root.left);
    prePrintTreeNode(root.right);
  }

  public static void midPrintTreeNode(TreeNode root) {
    if (root == null) {
      return;
    }
    midPrintTreeNode(root.left);
    System.out.print(root.val+ " ");
    midPrintTreeNode(root.right);
  }

  private static TreeNode rebuildBinaryTree20230331(int[] pre, int[] mid, int preStart, int preEnd, int midStart, int midEnd) {

    if (preStart > preEnd) {
      return null;
    }

    TreeNode root = new TreeNode(pre[preStart]);
    int k = 0;
    for (int i = midStart ; i <= midEnd ; i++) {
      if (mid[i] == pre[preStart]) {
        k =  i;
        break;
      }
    }
    root.left = rebuildBinaryTree20230331(pre, mid, preStart+1, preStart + k-midStart, midStart, k-1);
    root.right = rebuildBinaryTree20230331(pre, mid, preStart+k-midStart +1, preEnd, k+1, midEnd);
    return root;
  }

  // This method takes in an array of integers pre and an array of integers mid, and returns a binary tree
  private static TreeNode rebuildBinaryTree20230323(int[] pre, int[] mid, int preStart, int preEnd, int midStart, int midEnd) {
    // If the start and end indices are the same, return null
    if (preStart > preEnd) {
      return null;
    }
    // Set the index to the start of the array
    int index = midStart;
    // Create a new node with the value of the array at the start index
    TreeNode root = new TreeNode(pre[preStart]);
    // While the index is less than or equal to the end index
    while (index <= midEnd) {
      // If the value at the index is the same as the value at the start index
      if (mid[index] == pre[preStart]) {
        // Break out of the loop
        break;
      }
      // Increment the index
      index ++;
    }

    root.left = rebuildBinaryTree20230323(pre, mid, preStart+1, preStart + (index - midStart) , midStart, index-1);
    // Recursively call the method with the start and end indices of the left and right subtrees
    root.left = rebuildBinaryTree20230323(pre, mid, preStart+1, preStart + (index - midStart), midStart, index-1);
    root.right = rebuildBinaryTree20230323(pre, mid, preStart + (index - midStart) + 1, preEnd, index+1, midEnd);
    // Return the root node
    return root;
  }

}
