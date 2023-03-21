package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.Stack;

//二叉树的最大深度
public class BinaryTreeMaxDeep extends Sort {
    public static void main(String[] args) {
        Integer[] arr = createArray(100, 100);
        TreeNode tree = createBinaryTree(arr);
        int deep = maxDeep(tree);
        System.out.println("树的最大深度：" + deep);
        deep = maxDeep20230321(tree);
        System.out.println("树的最大深度：" + deep);
    }

    private static int maxDeep20230321(TreeNode tree) {
        if (tree == null) {
            return 0;
        }

        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> deepStack = new Stack<>();

        nodeStack.push(tree);
        deepStack.push(1);
        TreeNode p;
        Integer val, maxDeep = Integer.MIN_VALUE;
        while (!nodeStack.isEmpty()) {
            p = nodeStack.pop();
            val = deepStack.pop();
            if (p.left==null&&p.right==null) {
                if (val > maxDeep) {
                    maxDeep = val;
                }
            }else {
                if (p.left!=null) {
                    nodeStack.push(p.left);
                    deepStack.push(val + 1);
                }
                if (p.right!=null) {
                    nodeStack.push(p.right);
                    deepStack.push(val + 1);
                }
            }
        }
        return maxDeep;
    }

    private static int maxDeep(TreeNode tree) {
        if (tree == null) {
            return 0;
        }

        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> deepStack = new Stack<>();

        nodeStack.push(tree);
        deepStack.push(1);
        TreeNode p;
        Integer val, maxDeep = Integer.MIN_VALUE;
        while (!nodeStack.isEmpty()) {
            p = nodeStack.pop();
            val = deepStack.pop();
            if (p.left==null&&p.right==null) {
                if (val > maxDeep) {
                    maxDeep = val;
                }
            }else {
                if (p.right!=null) {
                    nodeStack.push(p.right);
                    deepStack.push(val + 1);
                }
                if (p.left!=null) {
                    nodeStack.push(p.left);
                    deepStack.push(val + 1);
                }
            }
        }
        return maxDeep;
    }
}
