package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DeepthFirstSearch extends Sort{


    public static void main(String[] args) {


        Integer[] arr = createIntegerArray(5);

        Sort.TreeNode head = createBinaryTree(arr);

        printArray("原生数组：", arr);
        ArrayList<Sort.TreeNode> treeNodeList = getDfsTreeResult(head);
        printTreeNodeList("结果：", treeNodeList);

        treeNodeList = getDfsTreeResult01(head);
        printTreeNodeList("结果：", treeNodeList);

    }

    private static ArrayList<TreeNode> getDfsTreeResult01(TreeNode head) {
        ArrayList<TreeNode> result = new ArrayList<>();
        if (head == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(head);

        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            result.add(temp);
            if (temp.right != null) {
                stack.push(temp.right);
            }
            if (temp.left != null) {
                stack.push(temp.left);
            }
        }
        return result;
    }

    private static ArrayList<TreeNode> getDfsTreeResult(TreeNode head) {

        ArrayList<TreeNode> treeNodeList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode node = head;
        stack.push(head);
        while (!stack.isEmpty()){

            node = stack.pop();
            treeNodeList.add(node);
            if (node.right!=null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return treeNodeList;
    }
}
