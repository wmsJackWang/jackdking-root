package org.jackdking.algorithm;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

public class ReverseLinkListBetween extends Sort {

    public static void main(String[] args) {
        ListNode listNode = getList(2, 20);

        ListNode result = reverseBetween(listNode, 2, 4);


    }

    public static ListNode reverseBetween (ListNode headRoot, int m, int n) {
        // write code here

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
        }

        ListNode list2 = end.next;
        end.next = null;

        reverseListNode(start.next);
        return list2;
    }

    private static void reverseListNode(ListNode next) {
    }
}
