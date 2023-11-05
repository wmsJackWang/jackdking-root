package org.jackdking.algorithm.revertlinkedlist;

import java.util.*;

import org.jackdking.algorithm.basesort.Sort;

public class RevertLinkedList extends Sort{



	public static void main(String[] args) {

		ListNode list = createList();
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
    ListNode list2 = revertLinkedList220313(list);
    printList(list2);

    //三索模型 解法
    list = createList();
    printList(list);
    ListNode list3 = revertLinkedList230314(list);
    printList(list3);

    //非递归
    list = createList();
    printList(list);
    list3 = revertLinkedList230318(list);
    printList(list3);

    //递归
    list = createList();
    printList(list);
    List<ListNode> revertList = new ArrayList<>();
    revertLinkedList230318DiGui(list, revertList);
    printList("反转后链表", revertList);
	}

  private static void revertLinkedList230318DiGui(ListNode list, List<ListNode> revertList) {

	  if (list == null) {
	    return;
    }
	  revertLinkedList230318DiGui(list.next, revertList);
	  revertList.add(list);
  }


  private static ListNode revertLinkedList230318(ListNode list) {
	  if (Objects.isNull(list)) {
	    return list;
    }

	  ListNode p1 = null, p2 = list, p3 = list;
	  while (p2 != null) {
	    p3 = p2.next;
	    p2.next = p1;
	    p1 = p2;
	    p2 = p3;
    }
	  return p1;
  }

  private static ListNode revertLinkedList230314(ListNode list) {
	  ListNode p1 = null , p2 = list, p3 = null;
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
  private static ListNode revertLinkedList220313(ListNode list) {

      if (Objects.isNull(list)) {
        return list;
      }
      ListNode p1 = list ,p2 = p1.next , p3;
      p1.next = null;
      while (p2 != null) {
        p3 = p2.next;
        p2.next = p1;
        p1 = p2;
        p2 = p3;
      }

      return p1;
  }


  public static void printList(List<ListNode> list) {
	  System.out.println();
	  for (ListNode node: list) {
	    System.out.print(node.value + " ");
    }
  }

  public static void printList(String desc, ListNode list) {

    System.out.println();
    System.out.print(desc + "：");
    ListNode head = list;
    while(head!=null)
    {
      System.out.print(head.value+" ");
      head = head.next;
    }
  }

  public static void printList(ListNode list) {

    System.out.println();
    System.out.print("打印链表：");
	  ListNode head = list;
		while(head!=null)
		{
			System.out.print(head.value+" ");
			head = head.next;
		}
	}

	//非递归的方式
	public static ArrayList<Integer> revertLinkedList(ListNode head) {

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
	public static ArrayList<Integer> revertLinkedListDiGui(ListNode head) {

		ArrayList<Integer> result = new ArrayList<Integer>();

		xx(head,result);

		return result;

	}

	private static void xx(ListNode head, ArrayList<Integer> result) {
		// TODO Auto-generated method stub

		if(head!=null)
		{
			xx(head.next,result);
			result.add(head.value);
		}

	}

	private static ListNode createList() {
		// TODO Auto-generated method stub
		ListNode head = null, p = null,pNode=null;

		for(int i = 0 ; i < 10 ; ++i)
			if(i==0)
			{
				pNode= new ListNode(new Random().nextInt(100));
				head = p = pNode;
			}
			else {
				pNode= new ListNode(new Random().nextInt(100));
				p.next=pNode;
				p = pNode;
			}

		return head;
	}

}
