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

        list = getList(new int[]{1,2,3,4,5,5,1,4,6});

        printList("原链表：", list);
        deleteDuplicates20230703(list);
        printList("现链表：", list);
    }

  private static ListNode deleteDuplicates20230703(ListNode list) {
      ListNode newList = list;
      HashSet<String> set = new HashSet<>();
      set.add(list.value + "");
      while (list.next!=null) {
        if (set.contains(list.next.value+"")) {
          list.next = list.next.next;
          continue;
        } else {
          set.add(list.next.value+"");
        }
        list = list.next;
      }
      return newList;
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
