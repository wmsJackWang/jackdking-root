package org.jackdking.algorithm.bytedance.last;

import com.fasterxml.jackson.databind.util.LinkedNode;
import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName KGroupReservelinkList
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2023 5:17 下午
 * @Version 2.0
 **/
public class KGroupReservelinkList extends Sort {

  public static void main(String[] args) {

    ListNode list = getList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13});
    ListNode resut = kGroupReserveLink(list, 3);
    printList("k组反转后，链表：", resut);

  }

  private static ListNode kGroupReserveLink(ListNode list, int k) {
    if (list==null) {
      return list;
    }
    int count =  0;
    ListNode head = list;
    while (head!=null) {
      count++;
      head = head.next;
    }
    if (k > count) {
      return list;
    }

    ListNode temp = new ListNode();
    temp.next = list;
    ListNode pre = temp, cur = list, next = cur.next;


    for (int i = 0 ; i < count/k; i++) {
      for (int j = 0 ; j < k-1 ; j++) {
        cur.next = cur.next.next;
        next.next = pre.next;
        pre.next = next;
        next = cur.next;
      }
      pre = cur;
      cur = cur.next;
      next = cur.next;
    }
    return temp.next;
  }
}
