package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName NodeListHasCycle
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 1:57 下午
 * @Version 2.0
 **/
/*
 * 链表是否存在循环
 * 扩展： 每K个一组反转链表、LRU Cache
 */
public class NodeListHasCycle extends Sort {
  public static void main(String[] args) {

    ListNode list = getList(10, 100);
    makeCycle(list);
    Boolean result = hashCycle(list);
    System.out.println("是否有循环:" + result);
  }

  private static void makeCycle(ListNode listNode) {
    listNode.next.next.next.next = listNode.next.next;
  }

  private static Boolean hashCycle(ListNode list) {
    Set<ListNode> set = new HashSet<>();
    while (list != null) {
      if (!set.add(list)) {
        return true;
      }
      list = list.next;
    }
    return false;
  }
}
