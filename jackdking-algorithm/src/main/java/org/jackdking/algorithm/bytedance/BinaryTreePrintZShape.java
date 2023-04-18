package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

//二叉树z字型打印
public class BinaryTreePrintZShape extends Sort {
  public static void main(String[] args) {

        Integer[] arr = createArray(20, 20);
        TreeNode tree = createBinaryTree(arr);
        ArrayList<ArrayList<Integer>> result = Print(tree);
        System.out.println(result);

        tree = createBinaryTree(arr);
        result = Print20230322(tree);
        System.out.println(result);

        tree = createBinaryTree(arr);
        result = Print20230419(tree);
        System.out.println(result);

  }

    private static ArrayList<ArrayList<Integer>> Print20230419(TreeNode tree) {

        Stack<TreeNode> left = new Stack<>();
        Stack<TreeNode> right = new Stack<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> detail ;
        TreeNode node = null;

        left.push(tree);
        while (!left.isEmpty()||!right.isEmpty()) {
            detail = new ArrayList<>();
            if (!left.isEmpty()) {
                while (!left.isEmpty()) {
                    node = left.pop();
                    detail.add(node.val);
                    if (node.right != null){
                        right.push(node.right);
                    }
                    if (node.left != null){
                        right.push(node.left);
                    }
                }
                result.add(detail);
                continue;
            }
            if (!right.isEmpty()) {
                while (!right.isEmpty()) {
                    node = right.pop();
                    detail.add(node.val);
                    if (node.left != null){
                        left.push(node.left);
                    }
                    if (node.right != null){
                        left.push(node.right);
                    }
                }
                result.add(detail);
            }

        }
        return result;

    }

    private static ArrayList<ArrayList<Integer>> Print20230322(TreeNode tree) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    if (tree==null){
      return result;
    }

    Stack<TreeNode> nodeStack1 = new Stack<>();
    Stack<TreeNode> nodeStack2 = new Stack<>();
    TreeNode p;
    ArrayList<Integer> list;
    nodeStack1.push(tree);
    while (!nodeStack1.isEmpty() || !nodeStack2.isEmpty()) {
      list = new ArrayList<>();
      while (!nodeStack1.isEmpty()) {
        p = nodeStack1.pop();
        if (p.right != null) {
          nodeStack2.push(p.right);
        }
        if (p.left != null) {
          nodeStack2.push(p.left);
        }
        list.add(p.val);
      }
      result.add(list);
      if (nodeStack2.isEmpty()) {
        return result;
      }
      list = new ArrayList<>();
      while (!nodeStack2.isEmpty()) {
        p = nodeStack2.pop();
        if (p.left !=null) {
          nodeStack1.push(p.left);
        }
        if (p.right != null) {
          nodeStack1.push(p.right);
        }
        list.add(p.val);
      }
      result.add(list);
    }
    return result;
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
