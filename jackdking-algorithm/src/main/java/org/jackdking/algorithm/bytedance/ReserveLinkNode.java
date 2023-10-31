package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ReserveLinkNode
 * @Description 链表反转
 * @Author jackdking
 * @Date 26/10/2023 1:12 下午
 * @Version 2.0
 *
 **/
public class ReserveLinkNode extends Sort {

  public static void main(String[] args) {

    ListNode link = getList(createArray(10));
    printList("原生链表：", link);

    ListNode newLink = reserveLinkNode(link);
    printList("反转后链表：", newLink);

    link = getList(createArray(10));
    printList("原生链表：", link);

    newLink = reserveLinkNode20231031(link);
    printList("反转后链表：", newLink);

  }

  private static ListNode reserveLinkNode20231031(ListNode link) {
    ListNode pre = null, current = link, next = null;

    while (current!=null) {
      next = current.next;
      current.next = pre;
      pre = current;
      current = next;
    }
    return pre;
  }

  private static ListNode reserveLinkNode(ListNode link) {
    ListNode pre = null, current = link, next = null;

    while (current != null) {
      next = current.next;
      current.next = pre;
      pre = current;
      current = next;
    }

    return pre;

  }


}
