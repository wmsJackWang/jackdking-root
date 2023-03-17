package org.jackdking.algorithm.revertlinkedlist;

import java.util.ArrayList;
import java.util.Objects;
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
		for(int i:result)
			System.out.print(i+" ");

    list = createList();
    printList(list);
    Node list2 = revertLinkedList220313(list);
    printList(list2);

    //三索模型 解法
    list = createList();
    printList(list);
    Node list3 = revertLinkedList230314(list);
    printList(list3);
	}

  private static Node revertLinkedList230314(Node list) {
	  Node p1 = null , p2 = list, p3 = null;
	  if (Objects.isNull(list)) {
	      return list;
    }

	  while (p2 != null) {
	    p3 = p2.next;
      p2.next = p1;
      p1 = p2;
	    p2 = p3;
    }
	  return p1;
  }

  //63 75 73 7 21 45 78 8 1 49 39 65 7 92 38 19 42 31 93 53
  private static Node revertLinkedList220313(Node list) {

      if (Objects.isNull(list)) {
        return list;
      }
      Node p1 = list ,p2 = p1.next , p3;
      p1.next = null;
      while (p2 != null) {
        p3 = p2.next;
        p2.next = p1;
        p1 = p2;
        p2 = p3;
      }

      return p1;
  }

  public static void printList(Node list) {

    System.out.println();
    System.out.print("打印链表：");
	  Node head = list;
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
				pNode= new Node(new Random().nextInt(100));
				head = p = pNode;
			}
			else {
				pNode= new Node(new Random().nextInt(100));
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
