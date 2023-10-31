package org.jackdking.algorithm.bytedance.last;

import org.jackdking.algorithm.basesort.Sort;

import java.util.Stack;

public class FindFirstCommonNode extends Sort {

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        Stack<ListNode> stack=new Stack<>();
        Stack<ListNode> stack2=new Stack<>();
        while(pHead1!=null)
        {
            stack.push(pHead1);
            pHead1=pHead1.next;
        }
        while(pHead2!=null)
        {
            stack2.push(pHead2);
            pHead2=pHead2.next;
        }
        ListNode head=null;
        while(!stack.isEmpty() && !stack2.isEmpty() && stack.peek()==stack2.peek() ){
            stack.pop();
            head=stack2.pop();
        }
        return head;
    }
}
