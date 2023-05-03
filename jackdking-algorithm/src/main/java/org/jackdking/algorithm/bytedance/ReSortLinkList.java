package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ReSortLinkList
 * @Description TODO
 * @Author jackdking
 * @Date 19/03/2023 12:18 下午
 * @Version 2.0
 **/
/*
 * 重排链表
 * 使用2n方式
 */
public class ReSortLinkList extends Sort {
  public static void main(String[] args) {

    ListNode list = getList(2, 11);
    printList("原链表", list);
    ListNode newList = reSortLinkList(list);
    printList("重排序后链表", newList);

    list = getList(10, 11);
    printList("原链表", list);
    newList = reSortLinkList20230319(list);
    printList("重排序后链表", newList);

    list = getList(10, 11);
    printList("原链表", list);
    newList = reSortLinkList20230417(list);
    printList("重排序后链表", newList);

  }

  private static ListNode reSortLinkList20230417(ListNode list) {
    int k = 0;
    ListNode newList = list, pre = null;
    while (list!=null){
      k++;
      list = list.next;
    }
    ListNode [] array = new ListNode[k];
    for (int i =0; i < k; i++) {
      array[i] = newList;
      newList = newList.next;
    }
    for (int i = 0 ; i < k/2; i++) {
      if (pre!=null) {
        pre.next = array[i];
      }
      array[i].next = array[k-1-i];
      pre = array[k-1-i];
    }
    if (k%2==0) {
      pre.next=null;
    } else {
      pre.next = array[k/2];
      pre.next.next = null;
    }
    return array[0];
  }


  private static ListNode reSortLinkList20230319(ListNode list) {
    if (list == null || list.next == null) {
      return list;
    }
    ArrayList<ListNode> nodeList = new ArrayList<>();
    ListNode p = list;
    while (p!=null) {
      nodeList.add(p);
      p = p.getNext();
    }
    int l = 0, r = nodeList.size()-1;
    while (l < r) {
      nodeList.get(l).next = nodeList.get(r);
      l++;
      nodeList.get(r).next = nodeList.get(l);
      r--;
    }
    nodeList.get(l).next = null;
    return list;
  }

  /*
   * 空间不足
   */
  private static ListNode reSortLinkList(ListNode list) {
    if (list ==null) {
      return list;
    }
    ListNode p = list;
    ArrayList<ListNode> nodeList = new ArrayList<>();
    while (p!=null) {
      nodeList.add(p);
      p = p.getNext();
    }

    int mid = (nodeList.size()-1)/2;
    ListNode newList = list, p1 = null;
    p = list;
    for (int i = 0 ; i <= mid ; i++) {
      p = nodeList.get(i);
      if (p1 != null) {
        p1.setNext(p);
      }
      p1 = nodeList.get(nodeList.size() - 1 - i);
      if (p1 != p) {
        p.setNext(p1);
      } else {
        p.setNext(null);
      }
    }
    return newList;
  }
}
