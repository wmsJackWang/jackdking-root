package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName MergeTwoLinkList
 * @Description TODO
 * @Author jackdking
 * @Date 31/07/2023 10:26 下午
 * @Version 2.0
 **/
public class MergeTwoSortedLinkList extends Sort {
  public static void main(String[] args) {

    ListNode list1 = getList(new int[]{2,5,7,9,12,34});
    printList("list1：", list1);
    ListNode list2 = getList(new int[]{3,4,10,14,20,39});
    printList("list2：", list2);
    ListNode newList = mergeTwoSortedLinkList(list1, list2);
    printList("合并后的list：", newList);

    list1 = getList(new int[]{2,5,7,9,12,34});
    printList("list1：", list1);
    list2 = getList(new int[]{3,4,10,14,20,39});
    printList("list2：", list2);
    newList = mergeTwoSortedLinkList20231031(list1, list2);
    printList("合并后的list：", newList);

  }

  private static ListNode mergeTwoSortedLinkList20231031(ListNode list1, ListNode list2) {
      ListNode newLink = new ListNode(-1);
      ListNode headNode = newLink;
      while (list1!= null && list2!=null) {
        if (list1.value < list2.value) {
          newLink.next = list1;
          list1 = list1.next;
        } else {
          newLink.next = list2;
          list2 = list2.next;
        }
        newLink = newLink.next;
      }
      if (list1!=null) {
        newLink.next = list1;
      }
      if (list2 != null) {
        newLink.next = list2;
      }
      return headNode.next;
  }

  private static ListNode mergeTwoSortedLinkList(ListNode list1, ListNode list2) {
    ListNode listNode = new ListNode(0);
    ListNode newHead = listNode;
    while (list1 != null && list2 != null) {
      if (list1.value < list2.value) {
        listNode.next = list1;
        list1 = list1.next;
      } else {
        listNode.next = list2;
        list2 = list2.next;
      }
      listNode = listNode.next;
    }
    if (list1 !=null) {
      listNode.next = list1;
    }
    if (list2 != null) {
      listNode.next = list2;
    }
    return newHead.next;
  }
}
