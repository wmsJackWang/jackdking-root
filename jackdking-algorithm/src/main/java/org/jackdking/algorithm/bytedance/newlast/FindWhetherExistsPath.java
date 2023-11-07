package org.jackdking.algorithm.bytedance.newlast;

import java.util.*;

/*
示例1:

 输入：n = 3, graph = [[0, 1], [0, 2], [1, 2], [1, 2]], start = 0, target = 2
 输出：true
示例2:

 输入：n = 5, graph = [[0, 1], [0, 2], [0, 4], [0, 4], [0, 1], [1, 3], [1, 4], [1, 3], [2, 3], [3, 4]], start = 0, target = 4
 输出 true
提示：

节点数量n在[0, 1e5]范围内。
节点编号大于等于 0 小于 n。
图中可能存在自环和平行边。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/route-between-nodes-lcci
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
————————————————
版权声明：本文为CSDN博主「Sinb妃」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_43981315/article/details/109145911


题解：
1、将每一个点可以到达的点放入哈希表中；
2、利用队列进行广度搜索；
3、获取队列头，利用哈希表找到队列头可以到达的所有点，并放入队列；
4、如果在到达的点中可以到达目标点，则返回true，如果最终队列空，说明两者之间无通路，返回false；
注意点：因为题中有自环，平行边，所以要进行判重，避免死循环。
————————————————
版权声明：本文为CSDN博主「Sinb妃」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_43981315/article/details/109145911
 */
public class FindWhetherExistsPath {

    public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
        Map<Integer, List<Integer>> map=new HashMap<>();
        for(int i=0;i<graph.length;i++)
        {
            if(map.containsKey(graph[i][0])){
                map.get(graph[i][0]).add(graph[i][1]);
                //   List<Integer> tmp=map.get(graph[i][0]);
                //    tmp.add(graph[i][1]);
            }
            else{
                List<Integer> tmp=new ArrayList<>();
                tmp.add(graph[i][1]);
                map.put(graph[i][0],tmp);
            }
        }
        Queue<Integer> queue=new LinkedList<>();
        queue.offer(start);
        boolean visit[]=new boolean[n];
        while(queue.size()>0)
        {
            int front=queue.poll();
            if(visit[front]) continue;
            else visit[front]=true;
            if(!map.containsKey(front)) continue;
            List<Integer> tmp=map.get(front);
            for(int i=0;i<tmp.size();i++)
            {
                int next=tmp.get(i);
                if(next==target) return true;
                else{
                    queue.offer(next);
                }
            }
        }
        return false;
    }
//————————————————
//    版权声明：本文为CSDN博主「Sinb妃」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/weixin_43981315/article/details/109145911
}
