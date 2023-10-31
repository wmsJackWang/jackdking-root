package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

public class ReservePrintLink extends Sort {

    public static void main(String[] args) {
        ListNode link = getList(new int[]{1,2,3,4,5,6,7,8});

        reservePrintLink(link);
    }

    private static void reservePrintLink(ListNode link) {
        if (link==null)
            return;
        reservePrintLink(link.next);
        System.out.print(" "+link.value);

    }

}
