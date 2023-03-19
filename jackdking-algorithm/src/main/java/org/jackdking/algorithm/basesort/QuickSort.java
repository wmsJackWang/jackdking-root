package org.jackdking.algorithm.basesort;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;


public class QuickSort extends Sort{



	public static void  quickSort(int[] array , int index1 , int index2) {

		if(index1>=index2)
			return ;

		int p1 , p2 , k;
		p1 = p2 = index1;
		for(; p2 < index2 ;++p2)
		{
			if(array[p2]<array[index2])
			{
				if(p2!=p1)
				{
					k = array[p1];
					array[p1] = array[p2];
					array[p2] = k;
				}
				p1++;
			}
		}

		if(p1!=index2)
		{
			k = array[index2];
			array[index2] = array[p1];
			array[p1] = k;
		}

		quickSort(array, index1, p1-1);
		quickSort(array, p1+1, index2);

	}

	public static void main(String[] args) {

//		int array[] = new int[] {1,56,34,3,5,3,12,32,21,21,345,78,90};
//		quickSort(array, 0, array.length-1);
//		printArray(array);
//		Integer i = new Integer(10);
//		Long l = new Long(10);
//		System.out.println(i.equals(l));
//		System.out.println(l.equals(i));
//
		HashMap<String, String> map = new HashMap<>();
		for(int i = 0 ;i<20;i++)
				map.put(i+"", i+"");

		Thread thread = new Thread(new Task(map));
		thread.start();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("test", "test");

	}


}


class Task implements Runnable{

	HashMap<String, String> map;

	public Task(HashMap<String, String> map ) {
		// TODO Auto-generated method stub
		this.map = map;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().getKey()+":"+iterator.next().getValue());
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
