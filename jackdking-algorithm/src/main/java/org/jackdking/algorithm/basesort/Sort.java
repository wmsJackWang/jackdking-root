package org.jackdking.algorithm.basesort;

import org.codehaus.groovy.tools.groovydoc.LinkArgument;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sort {

  /*
   *  重排链表
   *  链表中环的入口结点
   *  判断链表中是否有环
   *  二叉树根节点到叶子节点的所有路径和
   *  二叉树中的最大路径和
   *
   *  买卖股票的最好时机(一)
   *  二叉树中和为某一值的路径(二)
   *  二叉树中和为某一值的路径(一)
   *  大数乘法
   *  重建二叉树
   */
	public static void main(String[] args) {


		int [] array = {1793111,1704885,1533399,1841885,1106030,1075047,683720,1775812,384614,1748120,465909,1464784,371144,1169996,1547285,1815434,371770,1534437,1361914,1908006,1642892,940052,1214020,1293092,1974448,161119,1323434,556599,1373519,1464940,279574,262433,335617,109745,288046,1489090,1117600,1256093,846346,752014,786901,163280,110109,1518282,1233229,314395,369349,1417147,1865047,73156,798853,130502,574144,988607,265340,1552401,1727426,1346779,530528,281836,311013,1646911,839746,1411724,1353713,1885642,1218958,640981,1371397,1901432,82962,1432921,203321,1595713,321526,948973,1236208,1363959,934899,896793,1508384,548839,1814294,22183,725125,1952668,759735,1834610,12072,950119,837758,1318463,581829,776083,1773795,9111,166708,1983888,436686,992239,1494229,7269,91218,1582448,548987,1041088,1557663,1061803,181357,69709,1990660,614682,9689,1458544,877325,863806,930818,1818782,1005295,652631,1908046,1108124,1820235,396014,921750,194445,329391,271492,1231544,1713579,1211384,483379,921719,144907,768520,1923510,1172025,1142186,140381,1221299,314247,26366,429496,599445,57461,1150445,1885079,1508820,767293,968731,1498538,1484970,696383,1631789,191440,1378019,1252811,72983,7956,1743383,1589067,389357,1987464,805141,1503832,631207,1369959,1128676,1107546,784351,1807031,1626747,1080788,867275,1226917,1791668,413854,1211411,712406,1002491,1561140,42784,816874,382335,369016,1113067,62854,936132,62921,1509838};

		System.out.println(quickSortKVal(array,0,array.length-1,134));

		quickSort(array,0,array.length-1);

		System.out.println(array.length);
		printArray(array);
	}


  public static void printList(String desc, ListNode head) {
    System.out.println();
    System.out.print(desc + ":");
    while (head != null) {
      System.out.print(head.value + " ");
      head = head.next;
    }
  }


  public static int[] createArray(int length) {
    Random random = new Random();
    int array[] = new int[length];
    for (int i = 0 ; i < length ; i ++) {
      array[i] = random.nextInt(100);
    }
    return array;
  }


  public static void printArray(int[] array){
	  System.out.println();
		for(int i:array)
			System.out.print(i+" ");

		System.out.println();

	}
    public static void printListInteger(List<Integer> array){
        System.out.println();
        for(Integer i:array)
            System.out.print(i+" ");

        System.out.println();

    }

    public static void printListInteger(String desc, List<Integer> array){
        System.out.println();
        System.out.print(desc);
        for(Integer i:array)
            System.out.print(i+" ");

        System.out.println();

    }

    public static void printListStr(List<String> array){
        System.out.println();
        for(String i:array)
            System.out.print(i+" ");

        System.out.println();

    }

  public static void printArray(String desc, int[] array){
    System.out.println();
    System.out.print(desc + ":");
    for(int i:array)
      System.out.print(i+" ");
  }

  public static void printArray(String desc, Integer[] array){
    System.out.println();
    System.out.print(desc + ":");
    for(int i:array)
      System.out.print(i+" ");
  }

   public static void quickSort(int [] array , int index1 , int index2) {

       if(index1>=index2)
           return;
       int p1 = index1 , p2 = index2,key = array[p1];
       while(p1<p2){
           while(p1<p2&&array[p2]>=key)
               p2--;
           if(p1<p2)
               array[p1]=array[p2];
           while(p1<p2&&array[p1]<key)
               p1++;
           if(p1<p2)
               array[p2]=array[p1];
       }
       array[p1] = key;
       quickSort(array, index1, p1-1);
       quickSort(array, p1+1,index2);

   }


  public static class ListNode {
    public ListNode next;
    public int value;

    public ListNode(int val) {
      this.value = val;
    }
    public ListNode(ListNode next, int val) {
      this.next = next;
      this.value = val;
    }

    public ListNode getNext() {
      return next;
    }

    public void setNext(ListNode next) {
      this.next = next;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Node{" +
        "next=" + next +
        ", val=" + value +
        '}';
    }
  }

  static int index = 0;
  public static TreeNode createBinaryTree(Integer[] array){
    if (array.length == 0) {
    return null;
    }
    TreeNode treeNode = new TreeNode(array[0]);
    Deque<TreeNode> deque = new LinkedList<>();
    deque.add(treeNode);
    // 先左后右
    boolean isleft = true;
    for (int i = 1; i < array.length; i++) {
      TreeNode peek = deque.getFirst();
      if (isleft) {
        if (array[i] != null) {
          peek.left = new TreeNode(array[i]);
          //将左节点添加至队尾
          deque.offer(peek.left);
        }
        isleft = false;

      } else {
        if (array[i] != null) {
          peek.right = new TreeNode(array[i]);
          deque.offer(peek.right);
        }
        // 删除队头第一个元素
        deque.pollFirst();
        isleft = true;
      }
    }
    return treeNode;
  }

  public static ListNode getList(int[]array){
    ListNode head = new ListNode(array[0]),  p = head, cur;
    for(int i = 1 ; i < array.length ; i++) {
      cur = new ListNode(array[i]);
      cur.next = null;
      p.next = cur;
      p = cur;
    }
    return head;
  }

  public static ListNode getList(int offset, int limit){
    ListNode p = null, head = null, pre = null;
    for(int val = offset , i = 0 ; i < limit ; i++ ) {
      p = new ListNode(null, val);
      val += new Random().nextInt(100);
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

   public static int quickSortKVal(int [] array , int index1 , int index2 , int k){

       if(index1>=index2)
           return array[index1];
       int p1 = index1 , p2 = index2,key = array[p1];
       while(p1<p2){
           while(p1<p2&&array[p2]>=key)
               p2--;
           if(p1<p2)
               array[p1]=array[p2];
           while(p1<p2&&array[p1]<key)
               p1++;
           if(p1<p2)
               array[p2]=array[p1];
       }
       array[p1] = key;
       if(k-1==p1)return array[p1];
       if(k-1>p1)return quickSortKVal(array,p1+1,index2,k);
       else return quickSortKVal(array,index1,p1-1,k);
   }



  public static int[] createArray() {
    Random random = new Random();
    int array[] = new int[12];
    for (int i = 0 ; i < 12 ; i ++) {
      array[i] = random.nextInt(100);
    }
    return array;
  }

  public static Integer[] createArray(int n, int randomNum) {
    Random random = new Random();
    Integer array[] = new Integer[n];
    for (int i = 0 ; i < n ; i ++) {
      array[i] = random.nextInt(randomNum);
    }
    return array;
  }

   public static class TreeNode {
    public int val = 0;
    public TreeNode left = null;
    public TreeNode right = null;

    public TreeNode(int val){
      this.val = val;
    }

	}
}
