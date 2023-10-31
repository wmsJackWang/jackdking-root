package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.Stack;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TwoStackCreateQueue
 * @Description TODO
 * @Author jackdking
 * @Date 31/10/2023 5:05 下午
 * @Version 2.0
 **/
public class TwoStackCreateQueue extends Sort {
  public static void main(String[] args) {

    QueueTemp queue = new QueueTemp();
    queue.addEle(1);
    queue.addEle(3);
    System.out.println(queue.getEle());
    System.out.println(queue.getEle());
  }

  static class QueueTemp {

    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    public void addEle(Integer ele) {
      synchronized(QueueTemp.class) {
        stack1.push(ele);
      }
    }

    public Integer getEle() {
      synchronized (QueueTemp.class) {
          while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
          }
          Integer result = stack2.pop();
          while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
          }

          return result;

      }
    }
  }

}
