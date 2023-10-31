package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.LinkNodeMergeSort;
import org.jackdking.algorithm.basesort.Sort;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName FindLinkReserveKValue
 * @Description 查找链表倒数第k个数
 * @Author jackdking
 * @Date 31/10/2023 11:53 上午
 * @Version 2.0
 **/
public class FindLinkReserveKValue extends Sort {

    public static void main(String[] args) {

      ListNode link = getList(new int[]{1,2,3,4,5,6,7,8,9,10});
      printList("源链表", link);
      int result = findLinkReserveKValue(link, 4);
      System.out.println("链表倒数第k个节点的数据："+result);
    }

  private static int findLinkReserveKValue(ListNode link, int k) {

      ListNode p1, p2 = null;
      p1 = link;
      int index = 0;
      while (p1!=null) {
        p1 = p1.next;
        index++;

        if (index == k) {
          p2 = link;
        }
        if (index>k) {
          p2 = p2.next;
        }
        //最后一个节点
        if (p1 == null && p2 !=null) {
          return p2.value;
        }
      }
      return -1;
  }
}
