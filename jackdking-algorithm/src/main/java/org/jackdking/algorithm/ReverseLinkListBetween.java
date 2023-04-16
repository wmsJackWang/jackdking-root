package org.jackdking.algorithm;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

public class ReverseLinkListBetween extends Sort {

    public static void main(String[] args) {
        ListNode listNode = getList(new int[]{1,2,3,4,5});
        printList("原始链表：", listNode);

        ListNode result = reverseBetween(listNode, 2, 4);
        printList("部分反转后链表：", result);
    }

    public static ListNode reverseBetween (ListNode headRoot, int m, int n) {
        // write code here
        ListNode newHead = new ListNode(-1);
        newHead.next = head;

<<<<<<< HEAD
        ListNode start = null , end = null, newHead = new ListNode(0), head = headRoot;
        newHead.next = head;
        int i = 0;
        while (head != null) {
            if (i == m-1){
                start = head;
            }
            if (i == n){
                end = head;
            }
            head = head.next;
            i++;
=======
        ListNode pre = null, finalHead = newHead, newTail = null;
        int i = 0;
        while (newHead.next!=null) {
          if (m-1 == i) {
            pre = newHead;
          }
          if (n -1 == i) {
            break;
          }
          newHead = newHead.next;
          i++;
>>>>>>> 8e832250d9fa9d9613d9d0df13a0d911da7c583b
        }
        newTail = newHead.next.next;
        newHead.next.next = null;
        ListNode newLink = reverseListNode(pre.next, newTail);
        pre.next = newLink;
        return finalHead.next;

<<<<<<< HEAD
        ListNode list2 = end.next;
        end.next = null;

        reverseListNode(start.next);
        return list2;
    }
=======
//         reverseListNode(start.next);
>>>>>>> 8e832250d9fa9d9613d9d0df13a0d911da7c583b

    private static void reverseListNode(ListNode next) {
    }

  private static ListNode reverseListNode(ListNode head, ListNode newTail) {
      ListNode pre = newTail, cur, next;

      while (head!=null){
        next = head.next;
        head.next = pre;
        pre = head;
        head = next;
      }

      return pre;
  }
}
