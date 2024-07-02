package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * 题目描述
从上往下打印出二叉树的每个节点，同层节点从左至右打印

深度优先搜索算法（Breadth-First-Search）
深度优先算法是一种图形搜索算法。简单的说，BFS是从根节点开始，沿着树(图)的宽度遍历树(图)的节点。如果所有节点均被访问，则算法中止。一般用队列数据结构来辅助实现BFS算法。

算法步骤
1. 首先将根节点放入队列中。
2. 从队列中取出第一个节点，并检验它是否为目标。
如果找到目标，则结束搜寻并回传结果。
否则将它所有尚未检验过的直接子节点加入队列中。
3. 若队列为空，表示整张图都检查过了——亦即图中没有欲搜寻的目标。结束搜寻并回传“找不到目标”。
4. 重复步骤2
 */
public class BreadthFirstSearch extends Sort {

    public static void main(String[] args) {

        Integer[] arr = createIntegerArray(20);

        TreeNode head = createBinaryTree(arr);

        List<Integer> result = printFromTopToBottom(head);
        printListInteger("结果：", result);


        ArrayList<TreeNode> treeNodeList = getBfsTreeResult(head);
        printTreeNodeList("结果：", treeNodeList);

        treeNodeList = getBfsTreeResult01(head);
        printTreeNodeList("结果：", treeNodeList);


    }

    private static ArrayList<TreeNode> getBfsTreeResult01(TreeNode head) {

        ArrayList<TreeNode> result = new ArrayList<>();
        if (head == null) {
            return result;
        }
        ArrayDeque<TreeNode> queue = new ArrayDeque();
        queue.offer(head);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (temp.left != null) {
                queue.offer(temp.left);
            }
            if (temp.right != null) {
                queue.offer(temp.right);
            }
        }

        return result;
    }

    private static ArrayList<TreeNode> getBfsTreeResult(TreeNode head) {
        Queue<TreeNode> queue = new ArrayDeque<>();

        ArrayList<TreeNode> results = new ArrayList<>();
        queue.offer(head);
        TreeNode node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            results.add(node);
            if (node.left!=null) {
                queue.offer(node.left);
            }
            if (node.right!=null){
                queue.offer(node.right);
            }
        }
        return results;
    }


    public static ArrayList<Integer> printFromTopToBottom(TreeNode root) {
        if (root == null) {
          return new ArrayList<Integer>();
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        // 首先将根节点放入队列中
        queue.offer(root);
        while (!queue.isEmpty()) {// 队列不空,还有节点未搜索
            // 根节点出队
            TreeNode current = queue.poll();
            // 将根节点放入结果集中
            list.add(current.val);
            if (current.left != null) {// 左节点不空,先搜索左节点
                // 先将左节点放入队列
                queue.offer(current.left);
            }
            if (current.right != null) {// 右节点不空,搜索右节点
                // 再将右节点放入队列
                queue.offer(current.right);
            }
        }
        return list;
    }

}
