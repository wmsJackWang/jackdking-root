package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.util.ArrayList;
import java.util.Stack;

public class BinaryTreePrintZShape extends Sort {
    public static void main(String[] args) {

        Integer[] arr = createArray(20, 20);
        TreeNode tree = createBinaryTree(arr);
        ArrayList<ArrayList<Integer>> result = Print(tree);
        System.out.println(result);

    }

    public static ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) {
            return result;
        }
        Stack<TreeNode> nodeStack1 = new Stack<>();
        Stack<TreeNode> nodeStack2 = new Stack<>();
        nodeStack1.push(pRoot);
        TreeNode p;
        while (!nodeStack1.isEmpty() || !nodeStack2.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            while (!nodeStack1.isEmpty()) {
                p = nodeStack1.pop();
                list.add(p.val);
                if (p.left != null) {
                    nodeStack2.push(p.left);
                }
                if (p.right != null) {
                    nodeStack2.push(p.right);
                }
            }
            result.add(list);
            list = new ArrayList<>();
            while (!nodeStack2.isEmpty()) {
                p = nodeStack2.pop();
                list.add(p.val);
                if (p.right != null) {
                    nodeStack1.push(p.right);
                }
                if (p.left != null) {
                    nodeStack1.push(p.left);
                }
            }
            if (list.size() != 0){
                result.add(list);
            }

        }

        return result;

    }
}
