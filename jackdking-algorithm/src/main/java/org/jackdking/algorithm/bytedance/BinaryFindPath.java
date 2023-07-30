package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * 二叉树中和为某一值的路径(二)
    *
    * 输入一颗二叉树的根节点root和一个整数expectNumber，找出二叉树中结点值的和为expectNumber的所有路径。
    1.该题路径定义为从树的根结点开始往下一直到叶子结点所经过的结点
    2.叶子节点是指没有子节点的节点
    3.路径只能从父节点到子节点，不能从子节点到父节点
    4.总节点数目为n
 */
public class BinaryFindPath extends Sort {

    public static void main(String[] args) {
        Integer num = 22;
        Integer[] arr = new Integer[]{10,5,12,4,7};
//        Integer[] arr = createArray(10, 15);
        TreeNode list = createBinaryTree(arr);
        printArray("原数组", arr);
        ArrayList<ArrayList<Integer>> result = findPath(list, num);
        System.out.println("路径和:" + num);
        System.out.println("result:" + result);

        result = findPath20230703(list, num);
        System.out.println("路径和:" + num);
        System.out.println("result:" + result);
    }

  private static ArrayList<ArrayList<Integer>> findPath20230703(TreeNode list, Integer num) {
      Stack<Integer> valStack = new Stack<>();
      Stack<TreeNode> nodeStack = new Stack<>();
      Stack<ArrayList<Integer>> pathStack = new Stack<>();
      ArrayList<ArrayList<Integer>> results = new ArrayList<>();
      if (list==null) {
        return results;
      }

      TreeNode p, left, right;
      Integer val;
      ArrayList<Integer> path = new ArrayList<>(), newPath;
      path.add(list.val);
      valStack.push(list.val);
      nodeStack.push(list);
      pathStack.push(path);

      while (!nodeStack.isEmpty()) {
        p = nodeStack.pop();
        val = valStack.pop();
        path = pathStack.pop();
        left = p.left;
        right = p.right;

        if (left==null&&right==null &&val.intValue() == num.intValue()) {
            results.add(path);
        } else {
          if (left !=null) {
            nodeStack.push(left);
            valStack.push(val+left.val);
            newPath = new ArrayList<>(path);
            newPath.add(left.val);
            pathStack.push(newPath);
          }
          if (right!=null) {
            nodeStack.push(right);
            valStack.push(val+right.val);
            newPath = new ArrayList<>(path);
            newPath.add(right.val);
            pathStack.push(newPath);
          }
        }
      }
    return results;
  }

  private static ArrayList<ArrayList<Integer>> findPath(TreeNode list, Integer num) {

        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        if (list == null) {
            return paths;
        }
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> valStack = new Stack<>();
        Stack<ArrayList<Integer>> pathStack = new Stack<>();
        ArrayList<Integer> pa = new ArrayList<>();
        pa.add(list.val);

        nodeStack.push(list);
        valStack.push(list.val);
        pathStack.push(pa);
        TreeNode p ;
        Integer val ;
        ArrayList<Integer> path ;
        while (!nodeStack.isEmpty()) {
            p = nodeStack.pop();
            val = valStack.pop();
            path = pathStack.pop();
            if (p.left == null && p.right == null) {
                if (val.intValue() == num) {
                    paths.add(path);
                }
            } else {
                if (p.right != null) {
                    nodeStack.push(p.right);
                    valStack.push(p.right.val + val);
                    ArrayList<Integer> newPath = new ArrayList<>(path);
                    newPath.add(p.right.val);
                    pathStack.push(newPath);
                }
                if (p.left != null) {
                    nodeStack.push(p.left);
                    valStack.push(p.left.val + val);
                    ArrayList<Integer> newPath = new ArrayList<>(path);
                    newPath.add(p.left.val);
                    pathStack.push(newPath);
                }
            }
        }
        return paths;
    }
}
