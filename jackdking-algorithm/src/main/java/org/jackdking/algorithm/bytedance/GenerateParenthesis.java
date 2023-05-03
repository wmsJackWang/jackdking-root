package org.jackdking.algorithm.bytedance;

import org.jackdking.algorithm.basesort.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
 * 描述
给出n对括号，请编写一个函数来生成所有的由n对括号组成的合法组合。
例如，给出n=3，解集为：
"((()))", "(()())", "(())()", "()()()", "()(())"

数据范围：0 \le n \le 100≤n≤10
要求：空间复杂度 O(n)O(n)，时间复杂度 O(2^n)O(2
n
 )
示例1
输入：
1
复制
返回值：
["()"]
复制
示例2
输入：
2
复制
返回值：
["(())","()()"]
 */
public class GenerateParenthesis extends Sort {



    ArrayList<String> res = new ArrayList<>();
    int n;
    public ArrayList<String> generateParenthesis (int _n) {
        // write code here
        n = _n;
        dfs("",0,0);
        return res;
    }

    public void dfs(String s,int cnt1,int cnt2) {
        if(cnt1 == n && cnt2 == n) {
            res.add(s);
            return;
        }
        if(cnt1 < n) {
            dfs(s+"(",cnt1+1,cnt2);
        }
        if(cnt1 > cnt2 && cnt2 < n) {
            dfs(s+")",cnt1,cnt2+1);
        }
    }

    public ArrayList<String> generateParenthesisV2 (int n) {
        ArrayList<String> arr=new ArrayList<>();
        if(n==0) return null;
        // write code here
        //合法括号 不以)开头，不以(结尾
        //即()开头结尾
        //消去几个（）后，仍应该保持这个性质,比如())不行
        //一个合法序列插入另一个合法序列任意位置仍然合法
        //问题转化成，从一个括号开始，用其他括号慢慢插
        String init="()";
        if(n==1){
            arr.add(init);
            return arr;
        }
        HashSet<String> set=new HashSet<>();
        ArrayList<String> lastRes=generateParenthesis(n-1);
        for(String item:lastRes){
            //可插入空位个数枚举,2(n-1)+1=2n-1
            // 左括号不能插入到最后一个右括号后面，否则就
            for(int i=0;i<2*n-1;i++){
                String prefix;
                if(i==0) prefix="";
                else prefix=item.substring(0,i);

                String end;
                if(i==2*n-2) end="";
                else end=item.substring(i);
                set.add(prefix+"()"+end);
            }
        }
        for(Iterator<String> it = set.iterator(); it.hasNext();){
            arr.add(it.next());
        }
        return arr;
    }


}
