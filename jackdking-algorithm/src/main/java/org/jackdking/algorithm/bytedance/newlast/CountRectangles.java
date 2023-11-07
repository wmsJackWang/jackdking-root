package org.jackdking.algorithm.bytedance.newlast;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*
 给你一个二维整数数组 rectangles ，其中 rectangles[i] = [li, hi] 表示第 i 个矩形长为 li 高为 hi 。给你一个二维整数数组 points ，其中 points[j] = [xj, yj] 是坐标为 (xj, yj) 的一个点。

第 i 个矩形的 左下角 在 (0, 0) 处，右上角 在 (li, hi) 。

请你返回一个整数数组 count ，长度为 points.length，其中 count[j]是 包含 第 j 个点的矩形数目。

如果 0 <= xj <= li 且 0 <= yj <= hi ，那么我们说第 i 个矩形包含第 j 个点。如果一个点刚好在矩形的 边上 ，这个点也被视为被矩形包含。
 */
public class CountRectangles {


    /**
     *
     * @param points Point类一维数组
     * @return int整型
     * 计算 二维平面上，最多有多少个点位于同一直线上
    思路：MAX记录位于同一直线上最多的点数
    遍历所有的点
    1.记录与当前点结合 斜率不存在的点数，即垂直于x轴的点 n1
    2.重复出现的点数，即与当前点相同的点 n2
    3.记录与当前点结合，每个斜率下的点数（斜率存在） n3
    每次遍历，选出3和1情况下，同一斜率下，最多的点数max；
    将 max+重复点数n2  与当前最多的点数MAX比较(最后需要加上当前比较的点)
     */
    public int maxPoints (Point[] points) {
        // write code here
        int len = points.length;
        if(len<=2){
            return len;
        }
        int MAX=0;
        for(int i=0;i<len;i++){//遍历每一个点
            int n1=0,n2=0,n3=0;
            Map<Float,Integer> map = new HashMap<Float,Integer>(); //键：斜率； 值：该斜率下与点i结合的点数
            for(int j=0;j<len;j++){//遍历其它点，计算其他点与点i的斜率
                if(i==j){//1.跳过本次循环
                    continue;
                }
                if(points[i].x==points[j].x){
                    if(points[i].y==points[j].y){//2.点j与当前点i重合
                        n2++;
                    }else{//3.点j与点i在同一条垂直于x轴的直线上，即斜率不存在
                        n1++;
                    }
                }else{//4.正常情况下，计算其它点与点i结合后，不同斜率下点的个数
                    float k = (float)(points[i].y-points[j].y)/(points[i].x-points[j].x);//斜率(不要忘记强转为float型)
                    if(map.get(k)==null){//当前斜率下，第一次出现
                        map.put(k,1);
                    }else{//否则，更新斜率出现的次数
                        map.put(k,map.get(k)+1);
                    }
                }//else
            }//for
            //5.通过比较，获取与点i在同一条直线上，最多的点数
            int max = n1;
            for(float f:map.keySet()){//遍历集合中，所有斜率下的点数。获取出现次数最多的点
                max = Math.max(max,map.get(f));
            }
            MAX = Math.max(MAX,max+n2); //需加上重复出现的点数n2
        }//for
        return MAX+1;//加上当前的这个点
    }

}
