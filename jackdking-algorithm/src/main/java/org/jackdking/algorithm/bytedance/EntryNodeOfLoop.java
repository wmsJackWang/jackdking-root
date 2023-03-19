package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName EntryNodeOfLoop
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 1:35 下午
 * @Version 2.0
 **/
// 链表中环的入口结点 ,
public class EntryNodeOfLoop extends Sort {

  public static void main(String[] args) {
    ListNode listNode = getList(10, 10);
    printList("原链表", listNode);
    makeCycle(listNode);
    ListNode node = checkNodeOfLoop(listNode);
    Integer result = node == null ? null : node.value;
    System.out.println("循环入口：" + result);


  }

  private static void makeCycle(ListNode listNode) {
      listNode.next.next.next.next = listNode.next.next;
  }

  private static ListNode checkNodeOfLoop(ListNode listNode) {

    Set<ListNode> set = new HashSet<>();
    while (listNode != null) {
      if (!set.add(listNode)) {
        return listNode;
      }
      listNode = listNode.next;
    }
    return null;
  }

}
