package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DeleteDuplicatesV2 extends Sort {

    public static void main(String[] args) {

        ListNode list = getList(new int[]{1,2,3,4,5,5,1,4,6});

        printList("原链表：", list);
        list = deleteDuplicates(list);
        printList("现链表：", list);
        list = getList(new int[]{1,2,3,4,5,5,1,4,6});

        printList("原链表：", list);
        list = deleteDuplicates230625(list);
        printList("现链表：", list);


    }

  private static ListNode deleteDuplicates230625(ListNode list) {
      if (list == null) {
        return list;
      }

      ListNode newHead = list;
      HashMap<String, Integer> map = new HashMap();
      map.put(list.value+"", 1);
      while (list.next!=null){
        if (map.get(list.next.value+"")!=null) {
          map.put(list.next.value+"", 2);
          list.next = list.next.next;
        } else {
          map.put(list.next.value+"", 1);
          list = list.next;
        }
      }
      ListNode finalHead = new ListNode(-1);
      finalHead.next = newHead;
      newHead = finalHead;
      while (newHead.next!=null) {
        if (map.get(newHead.next.value+"") == 2) {
          newHead.next = newHead.next.next;
        } else {
          newHead = newHead.next;
        }
      }

      return finalHead.next;
  }

  private static ListNode deleteDuplicates(ListNode list) {
        ListNode newlist = list;
        if (list == null) {
            return list;
        }
        HashMap<String,Integer> set = new HashMap<>();
        Integer result = null;
        set.put(list.value+"", 1);

        while (list.next!=null) {
            result = set.put(list.next.value + "", 1);
            if (result != null) {
                set.put(list.next.value + "", 2);
                list.next = list.next.next;
            }else {
                list = list.next;
            }
        }
        list = newlist;
        newlist = new ListNode(-1);
        newlist.next = list;
        list = newlist;
        while (newlist.next != null){
            if (set.get(newlist.next.value+"") == 2) {
                newlist.next = newlist.next.next;
            }else {
                newlist = newlist.next;
            }
        }
        return list.next;
    }
}
