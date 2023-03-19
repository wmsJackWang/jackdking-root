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
    int result = quickSortFontK20230314(arr, 0, arr.length - 1, 12);
    System.out.println("\nk result val : " + result);

    arr = createArray();
    printArray("原数组", arr);
    quickSortFontK20230318(arr, 0, arr.length -1);
    printArray("快排后数组", arr);


    arr = createArray();
    printArray("原数组", arr);
    quickSortFontK20230318V2(arr, 0, arr.length -1);
    printArray("快排后数组", arr);
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

	    if (k > end) {
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

  private static int[] createArray() {
	    Random random = new Random();
	    int array[] = new int[12];
	    for (int i = 0 ; i < 12 ; i ++) {
	        array[i] = random.nextInt(100);
      }
	    return array;
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
