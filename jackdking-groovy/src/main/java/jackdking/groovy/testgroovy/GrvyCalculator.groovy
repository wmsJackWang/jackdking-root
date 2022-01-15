package jackdking.groovy.testgroovy

import java.text.SimpleDateFormat

class GrvyCalculator {
    //groovy语法
    def discount(a,b){
        def c = a * b
    }

    //java语法
    int add(int a, int b){
        return a + b;
    }

    //java语法
    String addStr(Map<Integer, String> map){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = sdf.parse("2017-12-08 08:00:00");

        String var1 = map.get(0);
        String var2 = map.get(1);
        String var3 = map.get(2);
        StringBuilder sb = new StringBuilder();
        return sb.append(var1).append("_").append(var2).append("_").append(var3).append("_").append(dateValue.toLocaleString());
    }
}