package org.jackdking.algorithm.bytedance.newlast;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.List;

/*
 K 个一组翻转链表
给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
方法一：将链表先变成List数组，List数组按K大小分成n块（有余数就为第n+1块），每块翻转（第n+1块不翻转），然后组成一个新的List数组，在按照新的list数组拼接成新的链表返回
————————————————
版权声明：本文为CSDN博主「harryptter」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/harryptter/article/details/133278367


方法二：递归 Java

解题思路
大致过程可以分解为 1、找到待翻转的k个节点（注意：若剩余数量小于 k 的话，则不需要反转，因此直接返回待翻转部分的头结点即可）。 2、对其进行翻转。并返回翻转后的头结点（注意：翻转为左闭又开区间，所以本轮操作的尾结点其实就是下一轮操作的头结点）。 3、对下一轮 k 个节点也进行翻转操作。 4、将上一轮翻转后的尾结点指向下一轮翻转后的头节点，即将每一轮翻转的k的节点连接起来。
————————————————
版权声明：本文为CSDN博主「harryptter」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/harryptter/article/details/133278367

 */
public class ReverseKGroup  extends Sort {

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }
        List<Integer> list = new ArrayList<>();
        List<Integer> resutlist = new ArrayList<>();
        while (head != null) {
            list.add(head.value);
            head = head.next;
        }

        int length = list.size();
        // 因为k<=length
        int n = length / k;
        int t = length % k;

        for (int i = 0; i < n; i++) {
            List<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                tempList.add(list.get(i * k + j));
            }
            resutlist.addAll(reverseList(tempList));
        }

        if (t != 0) {
            List<Integer> tempList = new ArrayList<>();
            for (int i = n * k; i <= length - 1; i++) {
                tempList.add(list.get(i));
            }
            resutlist.addAll(tempList);
        }

        ListNode node = setNodes(0, resutlist);
        return node;
    }

    public static ListNode setNodes(int index, List<Integer> nums) {
        ListNode res = new ListNode();
        res.value = nums.get(index);
        if (index == nums.size() - 1) {
            res.next = null;
            return res;
        } else {
            res.next = setNodes(index + 1, nums);
        }
        return res;
    }

    private static List<Integer> reverseList(List<Integer> reverseData) {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = reverseData.size() - 1; i >=0; i--) {
            arrayList.add(reverseData.get(i));
        }
        return arrayList;
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8,9,0};
        ListNode node = getList(nums);
        ListNode node2 =reverseKGroup(node,3);
        printList("k一组反转：", node2);
    }
//
//————————————————
//    版权声明：本文为CSDN博主「harryptter」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/harryptter/article/details/133278367


    //递归方法
    public static ListNode reverseKGroup2(ListNode head, int k) {
        //退出递归的条件
        if(head == null ) return head;
        ListNode tail = head;
        for(int i =0;i<k;i++){
            // if(tail == null) break; // 这个是不足k也反转
            if(tail == null) return head; // 不足k的节点，保持原来顺序
            tail = tail.next;
        }
        //反转前k个节点
        ListNode newHead = reverse(head, tail);
        //下一轮的开始还是tail节点，因为你是要确定下一次返回链表的头节点的位置
        head.next =  reverseKGroup(tail,k);
        return newHead;
    }
    public static ListNode reverse(ListNode head, ListNode tail){
        ListNode prev =null;
        ListNode cur = head;
        //只需要把原来判断尾节点为空的，改为在传入节点就行。
        while(cur !=tail){
            ListNode next = cur.next;
            cur.next = prev;
            prev =cur;
            cur = next;
        }
        return prev;
    }


}
