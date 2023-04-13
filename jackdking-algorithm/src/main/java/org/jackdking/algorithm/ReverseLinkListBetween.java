package org.jackdking.algorithm;

import org.jackdking.algorithm.basesort.Sort;
import org.jackdking.algorithm.treeorder.Tree;

public class ReverseLinkListBetween extends Sort {

    public static void main(String[] args) {
        ListNode listNode = getList(2, 20);

        ListNode result = reverseBetween(listNode, 2, 4);


    }

    public static ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here

        ListNode start = null , end = null;
        int i = 0;
        while (head != null) {
            if (i == m-1){
                start = head;
            }
            if (i == n ){
                end = head;
            }
        }

        ListNode list2 = end.next;
        end.next = null;

        reverseListNode(start.next);

    }
}
