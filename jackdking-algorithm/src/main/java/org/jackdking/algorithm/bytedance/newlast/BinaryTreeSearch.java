package org.jackdking.algorithm.bytedance.newlast;

import org.jackdking.algorithm.basesort.Sort;

import java.util.List;

//二叉树给定前中序求后序遍历
public class BinaryTreeSearch extends Sort {

    public static void main(String[] args) {
        TreeNode tree = createBinaryTree(new Integer[]{2,3,4,5,6,7,89,9});
        preSearch(tree);
        System.out.println();
        midSearch(tree);
        System.out.println();
        backSearch(tree);

    }

    private static void backSearch(TreeNode tree) {
        if (tree==null) {
            return ;
        }
        backSearch(tree.left);
        backSearch(tree.right);
        System.out.print(tree.val +" ");
    }

    private static void midSearch(TreeNode tree) {
        if (tree==null) {
            return ;
        }
        midSearch(tree.left);
        System.out.print(tree.val +" ");
        midSearch(tree.right);
    }

    private static void preSearch(TreeNode tree) {

        if (tree==null) {
            return ;
        }
        System.out.print(tree.val +" ");
        preSearch(tree.left);
        preSearch(tree.right);

    }


}
