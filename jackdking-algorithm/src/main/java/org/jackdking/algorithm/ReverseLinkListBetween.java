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

    public static ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here
        ListNode newHead = new ListNode(-1);
        newHead.next = head;

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
        }
        newTail = newHead.next.next;
        newHead.next.next = null;
        ListNode newLink = reverseListNode(pre.next, newTail);
        pre.next = newLink;
        return finalHead.next;

    }

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
