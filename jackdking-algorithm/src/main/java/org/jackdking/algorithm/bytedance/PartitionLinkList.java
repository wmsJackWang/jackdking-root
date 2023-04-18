package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

/*
 * 描述
给出一个长度为 n 的单链表和一个值 x ，单链表的每一个值为 listi ，请返回一个链表的头结点，要求新链表中小于 x 的节点全部在大于等于 x 的节点左侧，并且两个部分之内的节点之间与原来的链表要保持相对顺序不变。

例如：
给出 1 \to 4 \to 3 \to 2 \to 5 \to 21→4→3→2→5→2 和 x = 3x=3
返回 1 \to 2 \to 2 \to 4 \to 3 \to 51→2→2→4→3→5

数据范围： n \le 200n≤200 ， -100 \le list[i] \le 100−100≤list[i]≤100
进阶：时间复杂度 O(n)O(n) ， 空间复杂度 O(1)O(1)
示例1
输入：
{1,4,3,2,5,2},3
复制
返回值：
{1,2,2,4,3,5}
复制
示例2
输入：
{1,2,3,4,1},5
复制
返回值：
{1,2,3,4,1}

 */
public class PartitionLinkList extends Sort{


    public ListNode partition(ListNode head, int x) {
        //小链表的头
        ListNode smallHead = new ListNode(0);
        //大链表的头
        ListNode bigHead = new ListNode(0);
        //小链表的尾
        ListNode smallTail = smallHead;
        //大链表的尾
        ListNode bigTail = bigHead;
        //遍历head链表
        while (head != null) {
            if (head.value < x) {
                //如果当前节点的值小于x，则把当前节点挂到小链表的后面
                smallTail = smallTail.next = head;
            } else {//否则挂到大链表的后面
                bigTail = bigTail.next = head;
            }

            //继续循环下一个结点
            head = head.next;
        }
        //最后再把大小链表拼接在一块即可。
        smallTail.next = bigHead.next;
        bigTail.next = null;
        return smallHead.next;
    }

    public ListNode partitionV2 (ListNode head, int x) {
        ListNode left = new ListNode(0);//左链表的尾节点
        ListNode leftHead = left;//左链表的头节点
        ListNode rightHead = new ListNode(0);
        rightHead.next = head;
        ListNode pre = rightHead;//工作指针的前驱
        ListNode cur = pre.next;//工作指针
        while(cur!=null){
            if(cur.value<x){//把小于目标值的节点摘除，尾插到左链表中
                left.next = cur;
                pre.next = cur.next;
                cur.next = null;
                cur = pre.next;
                left = left.next;
            }
            else {
                pre = cur;
                cur = cur.next;
            }
        }
        left.next = rightHead.next;//把链表合起来，然后返回。
        return leftHead.next;
    }



}
