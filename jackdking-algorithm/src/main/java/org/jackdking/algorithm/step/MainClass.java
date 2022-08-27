package org.jackdking.algorithm.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class MainClass {

	

	public static void main(String[] args) throws InterruptedException {
		
		StepDetector mStepDetector = new StepDetector();
        StepCount mStepCount = new StepCount();
        MyStepValuePassListener mValuePassListener = new MyStepValuePassListener();
        mStepCount.initListener(mValuePassListener);
        mStepDetector.initListener(mStepCount);

        float[] values ;
        float[] oriValues = new float[3];
        List<String> result = new ArrayList<>();
        
        Scanner scanner = new Scanner(System.in);
        String data = "";
        String [] strarr ;
    	while(scanner.hasNext()) {
			data = scanner.nextLine();
			if(data.equals("exit"))break;
			strarr = data.split("	");
			oriValues[0] = Float.valueOf(strarr[0]);
			oriValues[1] = Float.valueOf(strarr[1]);
			oriValues[2] = Float.valueOf(strarr[2]);
	        
	        float gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
	      + oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
			result.add(String.valueOf(gravityNew));
			
    	}
        
    	values = new float[result.size()];
    	int i = 0;
    	for(String e : result) 
    	{
    		values[i++] = Float.valueOf(e);
    		System.out.println(e);
    	}
    		
        System.out.println("final step count: "+JdkUtil.getPeakNum3(values));

	}
}
 