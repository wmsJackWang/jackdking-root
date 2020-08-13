package org.jackdking.algorithm.revertlinkedlist;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import org.jackdking.algorithm.basesort.Sort;

public class RevertLinkedList extends Sort{
	
	
	
	public static void main(String[] args) {
		
		Node list = createList();
		ArrayList<Integer> result = null;
		
		//非递归的方式
//		result = revertLinkedList(list);
		
		//递归的方式
		result = revertLinkedListDiGui(list);
		
		printList(list);
		System.out.println("结果:");
		for(int i:result)
			System.out.print(i+" ");
	}
	
	public static void printList(Node head) {
		
		while(head!=null)
		{
			System.out.print(head.value+" ");
			head = head.next;
		}
	}
	
	
	//非递归的方式
	public static ArrayList<Integer> revertLinkedList(Node head) {
		
		Stack<Integer> stack = new Stack<Integer>();
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(head!=null)
		{
			stack.push(head.value);
			head = head.next;
		}
		
		while(!stack.isEmpty())
		{
			result.add(stack.pop());
		}
		
		return result;
		
	}
	
	//递归的方式
	public static ArrayList<Integer> revertLinkedListDiGui(Node head) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		
		xx(head,result);
		
		return result;
		
	}

	private static void xx(Node head, ArrayList<Integer> result) {
		// TODO Auto-generated method stub
		
		if(head!=null)
		{
			xx(head.next,result);
			result.add(head.value);
		}
		
	}

	private static Node createList() {
		// TODO Auto-generated method stub
		Node head = null, p = null,pNode=null;
		
		for(int i = 0 ; i < 10 ; ++i)
			if(i==0)
			{
				pNode= new Node(new Random().nextInt(30));
				head = p = pNode;
			}
			else {
				pNode= new Node(new Random().nextInt(30));
				p.next=pNode;
				p = pNode;
			}
		
		return head;
	}

}



class Node{
	int value;
	Node next;
	Node(int value){
		this.value = value;
	}
}