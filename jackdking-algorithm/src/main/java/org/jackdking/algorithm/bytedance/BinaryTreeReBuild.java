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

    pre = new int[]{0,1,3,4,2,5,6};
    mid = new int[]{3,1,4,0,5,2,6};
    tree = rebuildBinaryTree20230323(pre, mid, 0, pre.length-1, 0, mid.length-1);


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
}
