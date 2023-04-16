package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.HashSet;

public class DeleteDuplicates extends Sort {

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();

        ListNode list = getList(new int[]{1,2,3,4,5,5,1,4,6});

        printList("原链表：", list);
        deleteDuplicates(list);
        printList("现链表：", list);
        list = getList(new int[]{1,1});

        printList("原链表：", list);
        deleteDuplicates(list);
        printList("现链表：", list);
    }

    private static void deleteDuplicates(ListNode list) {
        if (list == null) {
            return;
        }
        HashSet<String> set = new HashSet<>();
        Boolean result = false;
        set.add(list.value+"");
        while (list.next!=null) {
            result = set.add(list.next.value + "");
            if (!result) {
                list.next = list.next.next;
            }else {
                list = list.next;
            }
        }
    }
}
