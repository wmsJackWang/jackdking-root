package org.jackdking.controller_samples.util;

import java.util.LinkedList;

public class Tree {
	public static void main(String[] args) {
		TreeNode tree = initTree();
		
		preOrderTraverse1(tree);
		System.out.println();
		preOrderTraverse2(tree);
	}
	
	
	static TreeNode initTree() {
		
		TreeNode n1 = new TreeNode("1");
		TreeNode n2 = new TreeNode("2");
		TreeNode n3 = new TreeNode("3");
		TreeNode n4 = new TreeNode("4");
		TreeNode n5 = new TreeNode("5");
		TreeNode n6 = new TreeNode("6");
		TreeNode n7 = new TreeNode("7");
		TreeNode n8 = new TreeNode("8");
		
		n5.left = n7;
		n5.right = n8;
		n2.left = n4;
		n2.right = n5;
		n3.right = n6;
		n1.left = n2;
		n1.right = n3;
		
		return n1;
	}
	

	//前序遍历——递归
    public static void preOrderTraverse1(TreeNode root) {  
    	
            if (root != null) {  
                System.out.print(root.val+"  ");  
                preOrderTraverse1(root.left);  
                preOrderTraverse1(root.right);  
            }  
    }  
	
    //前序遍历——循环+栈
    public static void preOrderTraverse2(TreeNode root) {  
            LinkedList<TreeNode> stack = new LinkedList<>();  
            TreeNode pNode = root;  
            while (pNode != null || !stack.isEmpty()) {  
                if (pNode != null) {  
                    System.out.print(pNode.val+"  ");  
                    stack.push(pNode);  
                    pNode = pNode.left;  
                } else { //pNode == null && !stack.isEmpty()  
                    TreeNode node = stack.pop();  
                    pNode = node.right;  
                }  
            }  
    }  
}

class TreeNode {
	
	String val;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(String s) {
		// TODO Auto-generated constructor stub
		this.val = s;
	}
}