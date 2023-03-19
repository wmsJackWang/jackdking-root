package org.jackdking.algorithm.basesort;

import java.util.Random;

public class ArrayMergeSort extends Sort{

	static int array[]= {12,34,3,45,5,89,67,7,8,78,9,9};
	static int temp [] = new int[array.length];

	public static void main(String[] args) {

		mergeSort(array,0,array.length-1);
		printArray(array);

    array = createArray();
    printArray(array);
    mergeSort20230315(array, 0, array.length-1);
    printArray(array);

    array = createArray();
    printArray(array);
    mergeSort20230316(array, 0, array.length - 1);
    printArray(array);

	}

  private static void mergeSort20230316(int[] array, int start, int end) {
	  if (start >= end) {
	    return;
    }
	  int mid = (start + end)/2;
	  mergeSort20230316(array, start, mid);
	  mergeSort20230316(array, mid + 1, end);
	  merge20230316(array, start, mid, end);
  }

  private static void merge20230316(int[] array, int start, int mid, int end) {
	  int p1 = start, p2 = mid + 1, p3 = start;
	  while (p1 <= mid && p2 <= end) {
	    if (array[p1] > array[p2]) {
	      temp[p3] = array[p1];
	      p1 ++;
      }else {
	      temp[p3] = array[p2];
	      p2 ++;
      }
	    p3 ++;
    }
	  while (p1 <= mid) {
	    temp[p3++] = array[p1++];
    }
    while (p2 <= end) {
      temp[p3++] = array[p2++];
    }
    for (int i = start ; i <= end ; i ++) {
      array[i] = temp[i];
    }

  }

  private static void mergeSort20230315(int[] array, int start, int end) {
      if (start>=end){
        return;
      }
      int mid = (start + end)/2;
      mergeSort20230315(array, start, mid);
      mergeSort20230315(array, mid +1, end);
      merge20230315(array, start, mid, end);

  }

  private static void merge20230315(int[] array, int start, int mid, int end) {
      int p1 = start, p2 = mid+1, p3 = start;
      while (p1 <= mid && p2 <= end) {
        if (array[p1] < array[p2]) {
          temp[p3] = array[p1];
          p1++;
        } else {
          temp[p3] = array[p2];
          p2++;
        }
        p3++;
      }
      while (p1 <= mid) {
        temp[p3++] = array[p1++];
      }
      while (p2 <= end) {
        temp[p3++] = array[p2++];
      }

      for(int index = start ; index <= end ; index ++) {
        array[index] = temp[index];
      }
  }

  private static int[] createArray() {
    Random random = new Random();
    int array[] = new int[12];
    for (int i = 0 ; i < 12 ; i ++) {
      array[i] = random.nextInt(100);
    }
    return array;
  }


  public static void mergeSort(int [] array , int index1,int index2) {


		if(index1>=index2)
			return;

		int middle = (index1+index2)/2;
		mergeSort(array, index1, middle);
		mergeSort(array, middle+1, index2);
		merge(array,index1,middle,index2);
	}

	public static void merge(int[] array2, int index1, int middle, int index2) {

		int x = index1,y = middle+1,z=index1;
		while(x<=middle&&y<=index2)
		{
			if(array2[x]>array2[y])//将大得值放到数组的前面，最后得到从大到小的数组。
				temp[z++]=array[x++];
			else
				temp[z++]=array[y++];
		}
		while(x<=middle)
		{
			temp[z++]=array[x++];
		}
		while(y<=index2)
		{
			temp[z++]=array[y++];
		}
		z = index1;

		while(z<=index2)
		{
			array2[z]=temp[z];
			++z;
		}

	}
}
