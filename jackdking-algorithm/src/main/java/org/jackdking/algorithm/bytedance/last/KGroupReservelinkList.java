package org.jackdking.algorithm.bytedance.last;

import com.fasterxml.jackson.databind.util.LinkedNode;
import org.jackdking.algorithm.basesort.Sort;

import java.util.Objects;

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

    list = getList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13});
    resut = kGroupReserveLink20240119(list, 3);
    printList("k组反转后，链表：", resut);

    list = getList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13});
    resut = kGroupReserveLink20240119V2(list, 3);
    printList("k组反转后，链表：", resut);

    list = getList(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13});
    resut = kGroupReserveLink20240122(list, 2);
    printList("k组反转后，链表：", resut);

}

  private static ListNode kGroupReserveLink20240122(ListNode list, int k) {
    if (Objects.isNull(list) || k <= 1) {
      return list;
    }
    int length = 0;
    ListNode p = list;
    while (p!=null) {
      length++;
      p = p.next;
    }
    int groupNum = length/k;

    ListNode vHeader = new ListNode(), current = list, next = current.next, tail = null;
    p = vHeader;
    for (int i = 0 ; i < groupNum ; i++) {

      for (int j = 0; j < k ; j++) {
        if (j == 0) {
          tail = current;
        }
        current.next = p.next;
        p.next = current;

        current = next;
        next = next.next;
      }
      tail.next = current;
      p = tail;
    }
    return vHeader.next;
  }

  private static ListNode kGroupReserveLink20240119V2(ListNode list, int k) {

     ListNode p = list;
     int length = 0;
     while (p!=null) {
       length++;
       p = p.next;
     }
     int groupNum = length/k;
     ListNode vHeader = new ListNode(), current = list, next = current.next, tail = null;
     p = vHeader;
     for (int i = 0; i < groupNum ; i++) {
       for (int j = 0 ; j < k ; j++){
          if (j==0) {
            tail = current;
          }
         current.next = p.next;
         p.next = current;
         current = next;
         next = next.next;
       }
       tail.next = current;
       p = tail;
     }
     return vHeader.next;
  }

  private static ListNode kGroupReserveLink20240119(ListNode list, int k) {
    if (k<=0 || list==null) {
      return list;
    }

    ListNode p = list;
    int length = 0;
    while (p!=null) {
      length ++;
      p = p.next;
    }
    ListNode vHeader = new ListNode(), current = list, next = current.next, tail = null;
    vHeader.next = list;
    p = vHeader;

    int groupNum = length/k;
    for (int i =0 ; i < groupNum ; i++) {
      for (int j = 0; j < k; j++) {
        if (j == 0 ) {
          tail = current;
        }
        current.next = p.next;
        p.next = current;
        current = next;
        next = next.next;
      }
      p = tail;
      p.next = current;

    }
    return vHeader.next;
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
