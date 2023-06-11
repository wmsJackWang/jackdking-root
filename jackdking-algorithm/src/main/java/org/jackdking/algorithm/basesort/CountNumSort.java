package org.jackdking.algorithm.basesort;

import java.util.Objects;
import java.util.Random;

//技术排序
//两种排序方式
public class CountNumSort extends Sort{

	public static void main(String[] args) {

		int array[]= {12,34,3,45,5,89,67,7,8,78,9,9};
		/*
		 * 第一种方法太浪费空间，如果数组存在特别大的值，那申请的数组空间也会非常的大。
		 *
		 */
		countNumSort(array);//3 5 7 8 9 9 12 34 45 67 78 89
		System.out.println();

		/*
		 * 就算取数组中 最大值和最小值的差值作为 统计数组的长度，但是大数问题还是存在
		 */
		countNumSortV2(array);

		countNumSort20220827(array);

		countNumSort20220827V2(array);
		array = createArray();
		printArray("原数组", array);
		array = countNumSort20230317(array);
		printArray("排序后数组", array);

		array = createArray();
		printArray("原数组", array);
		countNumSort20230317V2(array);
		printArray("排序后数组", array);

	}

	//直接取数组的最大值  作为统计数组的长度
	public static void countNumSort(int array[]){

		int max = 0 ;
		for(int e:array)
			if(max<e)
				max = e;


		int[] count = new int [max];//错误点:统计数组的长度是原数组最大值

		for(int i = 0 ;i<array.length;++i)
			count[i]=0;

		for(int e:array)
		{
			count[e-1]++;
		}

		for(int k = 0;k < count.length ; ++k)//错误点二:这里的循环必须使用索引 自加的形式,因为我们需要count数组的索引来输出排序结果。
			for(int i=0;i<count[k];i++)
				System.out.print((k+1)+" ");

	}

	//取原数组中 最大值和最小值的相差值作为 统计数组的长度
	public static void countNumSortV2(int array[]) {

		int max = array[0] ,  min = array[0];
		for(int e : array)
		{
			if(max < e)
				max = e;
			if(min > e)
				min = e;

		}
		int length = max - min + 1 ;
		int count[] = new int [length];
		for(int i = 0 ; i < count.length;++i)
			count[i] = 0;

		for(int e:array)
			count[e-min]++;

		for(int i = 0 ; i < count.length;++i)
			for(int j = 0 ; j < count[i] ; ++j)
				System.out.print((i+min)+" ");


	}



	private static void countNumSort20220827(int[] array) {

		int maxValue = 0;
		for (int v : array) {
			if (v > maxValue) {
				maxValue = v;
			}
		}

		int [] countArray = new int[maxValue];
		for (int v : array) {
			countArray[v-1] ++;
		}

		maxValue = 0;
		int index = 0;
		for (int i = 0; i < countArray.length ; i++) {
			if (maxValue < countArray[i]) {
				maxValue = countArray[i];
				index = i;
			}
		}

		System.out.println("数组中重复最多的数字："+ ++index);
	}


	private static void countNumSort20220827V2(int[] array) {

		if (Objects.isNull(array) || array.length == 0) {
			return;
		}

		int max = array[0], min = array[0];
		for (int v : array){
			if (v > max) {
				max = v;
			}

			if (v < min) {
				min = v;
			}
		}

		int[] countArray = new int[max - min + 1];

		for (int v : array) {
			countArray[v - min] ++;
		}

		int index = 0;
		max = 0;
		for (int i = 0 ; i < countArray.length ; i ++) {
			if (countArray[i] > max) {
				max = countArray[i];
				index = i;
			}
		}

		System.out.println("数组中重复最多的数字："+ (index+min));
	}

  private static void countNumSort20230317V2(int[] array) {
	  int min=0, max=0;
	  for(int i = 0 ; i < array.length ; i ++) {
	    if (array[i] > max) {
	      max = array[i];
      }
	    if (array[i] < min) {
	      min = array[i];
      }
    }
	  int mark[] = new int[max-min+1];
	  for (int i = 0 ; i < array.length ; i++) {
	    mark[array[i]] +=1;
    }
	  int k = 0;
	  for (int i = 0 ; i < mark.length ; ++i) {
	    for (int j = 0; j < mark[i]; j ++) {
        array[k ++] = i;
      }
    }
  }

  private static int[] countNumSort20230317(int[] array) {
	  int max = 0;
	  for (int i = 0 ; i < array.length ; i ++) {
	    if (array[i] > max) {
	      max = array[i];
      }
    }

	  System.out.println("max:" + max);
	  int[] mark = new int[max+1];
    for (int i = 0 ; i < array.length ; i ++) {
      mark[array[i]] += 1;
    }
    printArray("mark数组:", mark);
    int k = 0;
    for (int i = 0 ; i < mark.length ; i ++) {
      for(int j = 0 ; j < mark[i] ; j ++) {
        array[k ++] = (i);
      }
    }
    return array;
  }

  public static int[] createArray() {
    Random random = new Random();
    int array[] = new int[12];
    for (int i = 0 ; i < 12 ; i ++) {
      array[i] = random.nextInt(100);
    }
    return array;
  }

}
