package org.jackdking.algorithm.basesort;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * 堆排序
 */
public class HeapSort extends Sort{

	public static void main(String[] args) {
		Multimap<String,Object> scoreMultimap = ArrayListMultimap.create();
		Optional ops;
		Stream s;
		System.out.println(
			Optional.ofNullable(100).map(v -> v % 6).get()
		);

		/*
		 * 这个是基于子节点，那么只能跟父节点比较，一次比较复杂度是n
		 */
		int[] array= {12,34,3,45,5,89,67,7,8,78,9,9};
		HeapSort(array);
		printArray(array);

		/*
		 * 基于父节点比较，每次比较能与左右子节点进行大小比较，那么比较的时间复杂度就是n/2
		 */
		array = new int[]{12, 34, 3, 45, 5, 89, 67, 7, 8, 78, 9, 9};
		HeapSort2(array);
		printArray(array);

		/*
		 * 基于父节点比较，每次比较能与左右子节点进行大小比较，那么比较的时间复杂度就是n/2
		 */
		array = new int[]{12, 34, 3, 45, 5, 89, 67, 7, 8, 78, 9, 9};
		HeapSort20220706(array);
		printArray(array);

		/*
		 * 基于父节点比较，每次比较能与左右子节点进行大小比较，那么比较的时间复杂度就是n/2
		 */
		array = new int[]{12, 34, 3, 45, 5, 89, 67, 7, 8, 78, 9, 9};
		HeapSort20220825(array);
		printArray(array);
	}

	/*
	 * 我这个算法的时间复杂度是n的平方，因为我这个进行比较是基于子节点与父节点进行依次比较的思想
	 */
	public static void HeapSort(int [] array) {
		
		int k = 0;
		for(int index1 = array.length-1 ; index1 > 0 ; index1--)
		{
			for(int index2 = index1 ;index2>0 ; index2--)
			{
				if(array[(index2-1)/2]<array[index2])
				{
					k = array[index2];
					array[index2] = array[(index2-1)/2];
					array[(index2-1)/2] = k;
				}
			}
			
			k = array[0];
			array[0] = array[index1];
			array[index1] = k;
			
		}
		
	}
	
	/*
	 * 堆排序，基于父节点循环的方式，时间复杂度是 n*log2n.
	 */
	public static void HeapSort2(int [] array) {


		int k = 0, left, right;
		for (int index1 = array.length - 1; index1 > 0; index1--) {
			for (int index2 = (index1 - 1) / 2; index2 >= 0; index2--) {
				left = (index2 + 1) * 2 - 1;
				right = (index2 + 1) * 2;

				int index = left;
				if (right <= index1)//防止一开始的时候右节点不存在，导致数组越界错误。
					index = array[left] > array[right] ? left : right;
				int x = array[index] > array[index2] ? index : index2;
				if (x != index2) {
					k = array[index2];
					array[index2] = array[x];
					array[x] = k;
				}
			}
			k = array[0];
			array[0] = array[index1];
			array[index1] = k;
		}
	}

	/**
	 * description: practice for heapsort <br>
	 * version: 1.0 <br>
	 * date: 06/07/2022 9:55 下午 <br>
	 * end:
	 * author: jackdking <br>
	 * @param : null
	 * @return: null
	 **/
	private static void HeapSort20220706(int[] array) {
		for (int p = array.length-1 ; p > 0 ; p--){
			for(int i = (p-1)/2 ; i >=0 ; i --){
				if (array[i]> array[i*2+1]) {
					exchange(array, i , i*2+1);
				}

				if (i*2+2 <= p && array[i]> array[i*2+2]) {
					exchange(array, i , i*2+2);
				}
			}
			exchange(array, 0, p);
		}
	}
	public static void exchange(int [] arr, int i, int j){
		int k = arr[i];
		arr[i] = arr[j];
		arr[j] = k;
	}

	/**
	 * description: practice for heapsort <br>
	 * version: 1.0 <br>
	 * date: 08/25/2022 11:58 下午 <br>
	 * end:
	 * author: jackdking <br>
	 * @param : null
	 * @return: null
	 **/
	private static void HeapSort20220825(int[] array) {
		if (Objects.isNull(array) || array.length == 0) {
			return;
		}

		int k;

		for (int index1 = array.length-1 ; index1 > 0 ; index1 --) {
			for (int index2 = (index1-1)/2 ; index2 >=0 ; index2 --) {
				if (index2*2+2 <= index1 && array[index2*2+2] > array[index2]) {
					k = array[index2*2+2];
					array[index2*2+2] = array[index2];
					array[index2] = k;
				}

				if (array[index2*2+1] > array[index2]) {
					k = array[index2*2+1];
					array[index2*2+1] = array[index2];
					array[index2] = k;
				}
			}

			//出现过问题：把值交换逻辑搞混了。
			k = array[index1];
			array[index1] = array[0];
			array[0] = k;

		}
	}
}
