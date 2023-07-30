package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.util.ArrayList;
import java.util.Stack;

/*
 * 二叉树中和为某一值的路径(一)
 * 给定一个二叉树root和一个值 sum ，判断是否有从根节点到叶子节点的节点值之和等于 sum 的路径。
    1.该题路径定义为从树的根结点开始往下一直到叶子结点所经过的结点
    2.叶子节点是指没有子节点的节点
    3.路径只能从父节点到子节点，不能从子节点到父节点
    4.总节点数目为n
 */
public class BinaryFindPathExist extends Sort{
    public static void main(String[] args) {

        Integer num = 22;
        Integer[] arr = new Integer[]{10,5,12,4,7};
//        Integer[] arr = createArray(10, 15);
        Sort.TreeNode list = createBinaryTree(arr);
        printArray("原数组", arr);
        Boolean result = findPath(list, num);
        System.out.println("路径和:" + num);
        System.out.println("result:" + result);

        printArray("原数组", arr);
        result = findPath20230703(list, num);
        System.out.println("路径和:" + num);
        System.out.println("result:" + result);
    }

  private static Boolean findPath20230703(TreeNode list, Integer num) {

      if (list == null) {
        return false;
      }

      Stack<TreeNode> nodeStack = new Stack<>();
      Stack<Integer> valStack = new Stack<>();
      nodeStack.push(list);
      valStack.push(list.val);
      TreeNode p, left, right;
      Integer val;

      while (!nodeStack.isEmpty()) {
        p = nodeStack.pop();
        val = valStack.pop();
        left = p.left;
        right = p.right;

        //只在叶子节点做判断
        if(left==null && right == null) {
          return val.intValue()==num.intValue(); //判断路劲值是否相等
        }
        if (left!=null) {
          nodeStack.push(left);
          valStack.push(val+ left.val);
        }
        if (right !=null) {
          nodeStack.push(right);
          valStack.push(val+ right.val);
        }
      }
      return false;
  }

  private static boolean findPath(Sort.TreeNode list, Integer num) {

        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        if (list == null) {
            return false;
        }
        Stack<Sort.TreeNode> nodeStack = new Stack<>();
        Stack<Integer> valStack = new Stack<>();
        Stack<ArrayList<Integer>> pathStack = new Stack<>();
        ArrayList<Integer> pa = new ArrayList<>();
        pa.add(list.val);

        nodeStack.push(list);
        valStack.push(list.val);
        pathStack.push(pa);
        Sort.TreeNode p ;
        Integer val ;
        ArrayList<Integer> path ;
        while (!nodeStack.isEmpty()) {
            p = nodeStack.pop();
            val = valStack.pop();
            path = pathStack.pop();
            if (p.left == null && p.right == null) {
                if (val.intValue() == num) {
                    return true;
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
        return false;
    }
}
