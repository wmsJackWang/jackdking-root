package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;

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
