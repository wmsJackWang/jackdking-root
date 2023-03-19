package org.jackdking.algorithm.basesort;

import java.util.Objects;

public class LinkListMergeSort {

    public static void main(String[] args) {
        Node one , two;
        one = getList(1, 15);
        two = getList(10, 15);
//        printList(one);
//        printList(two);

        printList(merge(one, two));

        one = getList(1, 15);
        printList(one);
        two = getList(10, 15);
        printList(two);
        //3索引模型
        printList(merge230315(one, two));

    }

  private static Node merge230315(Node one, Node two) {

      if (Objects.isNull(one)) {
          return two;
      }

      if (Objects.isNull(two)) {
          return one;
      }
      Node header , p1 = one, p2 = two, p3;
      if (p1.val < p2.val){
          header = p1;
          p3 = p1;
          p1 = p1.next;
      } else {
          header = p2;
          p3 = p2;
          p2 = p2.next;
      }

      while (p1 != null && p2 != null) {
          if (p1.val < p2.val) {
              p3.next = p1;
              p1 = p1.next;
          } else {
              p3.next = p2;
              p2 = p2.next;
          }
          p3 = p3.next;
      }
      if (p1 == null) {
          p3.next = p2;
      }
      if (p2 == null) {
          p3.next = p1;
      }


      return header;
  }

  public static void printList(Node head) {
        System.out.println();
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    public static Node getList(int offset, int limit){
        Node p = null, head = null, pre = null;
        for(int val = offset , i = 0 ; i < limit ; i++ ) {
            p = new Node(null, val);
            val += 2;
            if (head == null) {
                head = p;
                pre = p;
            }

            if (pre != null) {
                pre.next = p;
            }
            pre = pre.next;
        }
        return head;
    }

    public static Node getReserveList(int offset, int limit){
        Node p = null,head = null;
        for(int val = offset , i = 0 ; i < limit ; i++ ) {
            p = new Node(null, val++);
            if (head != null) {
                p.next = head;
            }
            head = p;
        }
        return head;
    }

    public static Node merge(Node one, Node two) {
        Node p1 = one,p2 = two;
        Node newHead = null, p = null;
        while(p1!=null && p2!= null){
            if(p1.val>p2.val){
                if(newHead == null){
                    newHead = p2;
                    p = newHead;
                }else{
                    p.next = p2;
                    p = p.next;
                }
                p2=p2.next;
            }else{
                if(newHead == null){
                    newHead = p1;
                    p = newHead;
                }else{
                    p.next = p1;
                    p = p.next;
                }
                p1=p1.next;
            }
        }

        if (newHead == null){
            newHead = one != null ? one : two;
        }else {
            if (p1 != null) p.next = p1;
            if (p2 != null) p.next = p2;
        }


        return newHead;
    }

    static class Node {
        Node next;
        int val;

        Node(Node next, int val) {
            this.next = next;
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "next=" + next +
                    ", val=" + val +
                    '}';
        }
    }

}
