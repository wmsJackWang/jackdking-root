package org.jackdking.algorithm.step;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JdkUtil {
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		File x = new File("D://x.txt");
		File y = new File("D://y.txt");
		File z = new File("D://z.txt");
		
		List<String> valxs = new ArrayList<String>();
		List<String> valys = new ArrayList<String>();
		List<String> valzs = new ArrayList<String>();
		
		
		FileWriter  fwx = new FileWriter(x);
		FileWriter  fwy = new FileWriter(y);
		FileWriter  fwz = new FileWriter(z);
		String data ="";
		String [] strarr ;
		while(scanner.hasNext()) {
			data = scanner.nextLine();
			if(data.equals("exit"))break;
			strarr = data.split(" ");
			if(!valxs.contains(strarr[0])) {
//					valxs.add(strarr[0]);
					fwx.write(strarr[0]+"\n");
			}
			
			if(!valys.contains(strarr[1])) {
//				valys.add(strarr[1]);
				fwy.write(strarr[1]+"\n");
			}
			
			if(!valzs.contains(strarr[2])) {
//				valzs.add(strarr[2]);
				fwz.write(strarr[2]+"\n");
			}
//			System.out.println(data);
		}

		fwx.flush();
		fwy.flush();
		fwz.flush();
		fwx.close();
		fwy.close();
		fwz.close();
		
}

	public static int getPeakNum3(float[] data){
        int peak=0;
 
        float[] PeakAndTrough=new float[data.length];
 
        //需要三个不同的值进行比较，取lo,mid，hi分别为三值
        for (int lo=0,mid=1,hi=2;hi<data.length;hi++){
            //先令data[lo]不等于data[mid]
            while (mid<data.length&&data[mid]==data[lo]){
                mid++;
            }
 
            hi=mid+1;
 
            //令data[hi]不等于data[mid]
            while (hi<data.length&&data[hi]==data[mid]){
                hi++;
            }
 
            if (hi>=data.length){
                break;
            }
 
            //检测是否为峰值
            if (data[mid]>data[lo]&&data[mid]>data[hi]){
                PeakAndTrough[mid]=1;       //1代表波峰
            }else if(data[mid]<data[lo]&&data[mid]<data[hi]){
                PeakAndTrough[mid]=-1;      //-1代表波谷
            }
 
            lo=mid;
            mid=hi;
        }
 
        //计算均值
        float ave=0;
        for (int i=0;i<data.length;i++){
            ave+=data[i];
        }
        ave/=data.length;
 
        //排除大于均值的波谷和小于均值的波峰
        for (int i=0;i<PeakAndTrough.length;i++){
            if ((PeakAndTrough[i]>0&&data[i]<ave)||(PeakAndTrough[i]<0&&data[i]>ave)){
                PeakAndTrough[i]=0;
            }
        }
 
        //统计波峰数量
        for (int i=0;i<PeakAndTrough.length;){
            while (i<PeakAndTrough.length&&PeakAndTrough[i]<=0){
                i++;
            }
 
            if (i>=PeakAndTrough.length){
                break;
            }
 
            peak++;
 
            while (i<PeakAndTrough.length&&PeakAndTrough[i]>=0){
                i++;
            }
        }
 
        return peak;
    }
	
	
}
