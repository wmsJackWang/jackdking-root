package org.jackdking.algorithm.basesort;

import java.util.Arrays;
import java.util.Random;

public class QuickSortV2 extends Sort{

	private static int[] arr = {12,23,22,100,-9,-1,10,100,77,234324343,645454,100,3223,34656,-12121};

	public static void main(String[] args) {

		System.out.println(Arrays.toString(arr));

		quickSort(arr, 0, arr.length-1);

		System.out.println(Arrays.toString(arr));

    arr = createArray();
    printArray(arr);
    // 注意递归函数中,双索引的起始值为start，而不是0
		quickSort20230314(arr, 0, arr.length - 1);
    printArray(arr);

    arr = createArray();
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    int result = quickSortFontK20230314(arr, 0, arr.length - 1, 4);
    System.out.println("\nk result val : " + result);
    quickSort(arr, 0, arr.length-1);
    printArray("快排后数组", arr);

    arr = createArray();
    printArray("原数组", arr);
    quickSortFontK20230318(arr, 0, arr.length -1);
    printArray("快排后数组", arr);

    arr = createArray();
    printArray("原数组", arr);
    quickSortFontK20230318V2(arr, 0, arr.length -1);
    printArray("快排后数组", arr);

    arr = createArray();
    printArray("原数组", arr);
    quickSortFontK20230424(arr, 0, arr.length -1);
    printArray("快排后数组", arr);

    arr = new int[]{12,23,22,100,-9,-1,10,100,77,234324343,645454,100,3223,34656,-12121};
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    result = quickSortFontK20230425(arr, 0, arr.length - 1, 7);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);

    arr = new int[]{12,23,22,100,-9,-1,10,100,77,234324343,645454,100,3223,34656,-12121};
    printArray("原数组", arr);
    quickSortFontK20230424(arr, 0, arr.length-1);
    printArray("数组快排后", arr);

    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    result = quickSortFontK20230522(arr, 0, arr.length - 1, 10);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);

    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    //求第十个数
    result = quickSortFontK20230605(arr, 0, arr.length - 1, 10-1);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);
    quickSortFontK20230424(arr, 0, arr.length -1);
    printArray("数组完整快排后", arr);



    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    //求第十个数
    result = quickSortFontK20230605V2(arr, 0, arr.length - 1, 10-1);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);


    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    quickSort20230625(arr, 0, arr.length - 1);
    printArray("数组快排后", arr);


    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    //求第十个数
    result = quickSortFontK20230704(arr, 0, arr.length - 1, 10-1);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);

    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    //求第十个数
    result = quickSortFontK20230710(arr, 0, arr.length - 1, 10-1);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);

    arr = createArray(30);
    printArray("原数组", arr);
    // 双索引模型初始值都是0，且范围都是[0, p1) [p1, p2)，p1 p2 划分了大小数区域,而[p2, end]则是待比较区域。
    //求第十个数
    result = quickSortFontK20230727(arr, 0, arr.length - 1, 26-1);
    System.out.println("\nk result val : " + result);
    printArray("数组前k快排后", arr);
	}

  private static int quickSortFontK20230727(int[] arr, int start, int end, int k) {
	  if (start > k || end <k) {
	    return -1;
    }

	  int p = start, q = start, n ;
	  for (; q < end ; q++) {
	    if (arr[end] > arr[q]) {
	      if (q != p) {
	        n = arr[q];
	        arr[q] = arr[p];
	        arr[p] = n;
        }
	      p++;
      }
    }
	  if (end != p) {
	    n = arr[end];
	    arr[end] = arr[p];
	    arr[p] = n;
    }
	  if (k == p) {
	    return arr[p];
    } else if (k < p) {
	    return quickSortFontK20230727(arr, start, p-1, k);
    } else {
      return quickSortFontK20230727(arr, p+1, end, k);
    }

  }

  private static int quickSortFontK20230710(int[] arr, int start, int end, int num) {

	    if (start >end) {
	      return -1;
      }
      int p = start, q = start, k;
      for (; q < end ; q++){
        if (arr[end] > arr[q]) {
          if (p != q) {
            k = arr[p];
            arr[p] = arr[q];
            arr[q] = k;
          }
          p++;
        }
      }
      if (end!=p) {
        k = arr[end];
        arr[end] = arr[p];
        arr[p] = k;
      }
      if (num == p) {
        return arr[p];
      } else if (p > num) {
        return quickSortFontK20230710(arr, start, p-1, num);
      } else {
        return quickSortFontK20230710(arr, p+1, end, num);
      }
  }

  private static int quickSortFontK20230704(int[] arr, int start, int end, int k) {
    if (start>end) {
      return -1;
    }

    int n, p=start, q=start;
    for (;q<end;q++){
      if (arr[end]>arr[q]) {
        if (p!=q) {
          n=arr[p];
          arr[p] = arr[q];
          arr[q] = n;
        }
        p++;
      }
    }
    if (end!=p) {
      n = arr[p];
      arr[p] = arr[end];
      arr[end] = n;
    }
    if (p == k){
      return arr[p];
    }else if (p >k) {
      return quickSortFontK20230704(arr, start, p-1, k);
    }else {
      return quickSortFontK20230704(arr, p+1, end, k);
    }
  }

  private static void quickSort20230625(int[] arr, int start, int end) {
	  if (start >= end) {
	    return;
    }
	  int k , p1 = start, p2 = start;
	  for (; p2 < end; p2++) {
	    if (arr[p2] < arr[end]) {

	      if (p1 != p2){
          k = arr[p1];
          arr[p1] = arr[p2];
          arr[p2] = k;
        }
	      p1++;
      }
    }
	  if (end != p1) {
	    k = arr[end];
	    arr[end] = arr[p1];
	    arr[p1] = k;
    }

	  quickSort20230625(arr, start, p1-1);
	  quickSort20230625(arr, p1+1, end);
  }

  private static int quickSortFontK20230605V2(int[] arr, int start, int end, int k) {
	  if (start > end || k > end) {
	    return -1;
    }
	  int p = start, q = start, n =0;
	  for (;q<end;q++){
	    if (arr[q] > arr[end]) {
	      if (q!=p) {
          n = arr[q];
          arr[q] = arr[p];
          arr[p] = n;
        }
        p++;
      }
    }
	  if (p!=end) {
	    n=arr[p];
	    arr[p] = arr[end];
	    arr[end] = n;
    }
	  if (k == p) {
	    return arr[p];
    } else if (k>p) {
	    return quickSortFontK20230605V2(arr, p+1, end, k);
    } else {
	    return quickSortFontK20230605V2(arr, start, p-1, k);
    }

  }

  private static int quickSortFontK20230605(int[] arr, int start, int end, int k) {
	  if (start > end || k > end) {
	    return -1;
    }
	  if (start ==k && end == k){
	    return arr[k];
    }

	  int p = start, q = start, n = 0;
	  for (; q < end ; q++){
      if (arr[q]< arr[end]) {

        if (q!=p) {
          n = arr[p];
          arr[p] = arr[q];
          arr[q] = n;
        }
        p++;
      }
    }
	  if (p != end) {
	    n = arr[p];
	    arr[p] = arr[end];
	    arr[end] = n;
    }
	  if (p == k) {
	    return arr[p];
    } else if (p > k) {
	    return quickSortFontK20230605(arr, start, p-1, k);
    } else {
	    return quickSortFontK20230605(arr, p+1, end, k);
    }

  }

  private static int quickSortFontK20230522(int[] arr, int i, int i1, int i2) {
	  if (i > i1) {
	    return -1;
    }
	  if (i1 <i2) {
	    return -1;
    }

	  int p1 = i , p2 = i, k;
    for (; p2 < i1 ; p2++) {
      if (arr[p2]< arr[i1]) {
        if (p1!=p2) {
          k = arr[p1];
          arr[p1] = arr[p2];
          arr[p2] = k;
        }
        p1++;
      }
    }
    if (i1 != p1) {
      k = arr[i1];
      arr[i1] = arr[p1];
      arr[p1] = k;
    }

    if (i2==p1){
      return arr[p1];
    } else if (i2 > p1) {
      return quickSortFontK20230425(arr, p1+1, i1, i2);
    } else {
      return quickSortFontK20230425(arr, i, p1-1, i2);
    }

  }

  private static int quickSortFontK20230425(int[] arr, int start, int end, int n) {
	  if (n > end) {
	    return -1;
    }
	  if (start>end){
	    return -1;
    }

    int p = start, q = start , k = 0;
    for (; q < end; q++){
      if (arr[q] < arr[end]) {
        if (p!=q) {
          k = arr[q];
          arr[q] =arr[p];
          arr[p] = k;
        }
        p++;
      }
    }
    if (p!=end) {
      k = arr[end];
      arr[end] = arr[p];
      arr[p] = k;
    }
    if (n == p) {
      return arr[p];
    }else if (n > p) {
      return quickSortFontK20230425(arr, p +1, end, n);
    }else {
      return quickSortFontK20230425(arr, start, p-1, n);
    }
  }

  private static void quickSortFontK20230424(int[] arr, int start, int end) {

	  if (start>=end) {
	    return;
    }

	  int p =start, q = start, k=0;
	  for (; q < end ; q++){
      if (arr[q] < arr[end]) {//只要存在，则一直移动
        if (q != p) {
          k = arr[q];
          arr[q] = arr[p];
          arr[p] = k;
        }
        p++;//q!=p,p也需要往后移动
      }
    }
	  if (end != p) {
	    k = arr[p];
	    arr[p] = arr[end];
	    arr[end] = k;
    }

	  quickSortFontK20230424(arr, start, p-1);
	  quickSortFontK20230424(arr, p+1, end);
  }

  private static void quickSortFontK20230318V2(int[] arr, int start, int end) {
	  if (start >= end) {
	    return;
    }
	  int p1 = start, p2 = start, k = 0;
    for (; p2 < end ; p2++) {
      if (arr[p2] > arr[end]) {
        if (p2!=p1) {
          k = arr[p1];
          arr[p1] = arr[p2];
          arr[p2] = k;
        }
        p1++;
      }
    }
    if (end != p1) {
      k = arr[end];
      arr[end] = arr[p1];
      arr[p1] = k;
    }
    quickSortFontK20230318V2(arr, start, p1 - 1);
    quickSortFontK20230318V2(arr, p1 + 1, end);

  }

  private static void quickSortFontK20230318(int[] arr, int start, int end) {

	  if (start >= end){
	    return;
    }

	  int p1 = start , p2 = start, k = 0;
    for ( ; p2 < end ; p2 ++) {
      if (arr[p2] > arr[end]) {
        if (p1 != p2) {
          k = arr[p1];
          arr[p1] = arr[p2];
          arr[p2] = k;
        }
        p1++;
      }
    }
    if (p1 != end) {
      k = arr[end];
      arr[end] = arr[p1];
      arr[p1] = k;
    }
    quickSortFontK20230318(arr, start, p1-1);
    quickSortFontK20230318(arr, p1 +1, end);
  }


  private static int quickSortFontK20230314(int[] arr, int start, int end, int k) {

	    if (k > end) {//超过最大索引值则默认为-1
	      return -1;
      }
	    if (start > end) {
	      return arr[k];
      }

	    int p1 = start, p2 = start, temp = 0;
      while(p2 < end) {
        if (arr[p2] < arr[end]) {
          if (p1 != p2) {
            temp = arr[p1];
            arr[p1] = arr[p2];
            arr[p2] = temp;
          }
          p1 ++;
        }
        p2 ++;
      }
      if (p1 != end){
        temp = arr[p1];
        arr[p1] = arr[end];
        arr[end] = temp;
      }
      if(p1 == k) {
        return arr[k];
      }
      if (k < p1) {
        return quickSortFontK20230314(arr, start, p1 - 1, k);
      }else {
        return quickSortFontK20230314(arr, p1 + 1, end, k);
      }
  }

  public static void printArray(int[] arr) {
	    System.out.println();
	    for (int i = 0 ; i < arr.length ; i ++) {
        System.out.print(arr[i] + " ");
      }
  }

  private static void quickSort20230314(int[] arr, int start, int end) {
	    if (start >= end){
	      return;
      }

	    int ref = arr[end];
	    int p1 = start , p2 = start, k;
	    while (p2 < end) {
        if (arr[p2] < ref) {
          if (p2 != p1) {
            k = arr[p1];
            arr[p1] = arr[p2];
            arr[p2] = k;
          }
          p1 ++;
        }
        p2 ++;
      }
	    if (p1 != end) {
	      k = arr[p1];
	      arr[p1] = arr[end];
	      arr[end] = k;
      }
	    quickSort20230314(arr, start, p1 - 1);
	    quickSort20230314(arr, p1 + 1, end);
  }

  public static void quickSort(int [] array , int index1 , int index2){

		if(index1>=index2)
			return ;

		int p1 = index1,p2 = index2 ,k = array[index1];
	    // 基准数据
	    int key = array[p1];
	    while (p1 < p2) {
	        // 因为默认基准是从左边开始，所以从右边开始比较
	        // 当队尾的元素大于等于基准数据 时,就一直向前挪动 p2 指针
	        while (p1 < p2 && array[p2] >= key) {
	            p2--;
	        }
	        // 当找到比 array[p1] 小的时，就把后面的值 array[p2] 赋给它
	        if (p1 < p2) {
	            array[p1] = array[p2];
	        }
	        // 当队首元素小于等于基准数据 时,就一直向后挪动 p1 指针
	        while (p1 < p2 && array[p1] < key) {
	            p1++;
	        }
	        // 当找到比 array[p2] 大的时，就把前面的值 array[p1] 赋给它
	        if (p1 < p2) {
	            array[p2] = array[p1];
	        }
	    }

	    array[p1] = key;

	    quickSort(array, index1, p1-1);
	    quickSort(array, p1+1, index2);


	}

}
