package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ReBuildBinaryTree
 * @Description TODO
 * @Author jackdking
 * @Date 20/03/2023 6:20 下午
 * @Version 2.0
 **/
public class ReBuildBinaryTree extends Sort {

  public static void main(String[] args) {
    int[] pre = new int[]{0,1,3,4,2,5,6};
    int[] mid = new int[]{3,1,4,0,5,2,6};

    //根据前序、中序来重建数组。
    TreeNode tree = rebuildBinaryTree(pre, mid, 0, pre.length-1, 0, mid.length-1);

    System.out.println(tree.left.val);
  }

  public static TreeNode rebuildBinaryTree(int[] pre, int[] mid, int preStart, int preEnd, int midStart, int midEnd) {

    if (preStart>preEnd) {
      return null;
    }
    TreeNode root = new TreeNode(pre[preStart]);
    int k = midStart;
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
