package jackdking.groovy.testgroovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import jackdking.groovy.Application;
import org.assertj.core.util.Maps;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GroovyTest
 * @Description TODO
 * @Author jackdking
 * @Date 12/01/2022 4:05 下午
 * @Version 2.0
 **/
public class GroovyTest {

    public static void main(String[] args) throws IOException {
//        way_one();
        way_two();
    }

    private static void way_two() throws IOException {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");

        ClassLoader cl = new GroovyTest().getClass().getClassLoader();
        GroovyClassLoader grvyCl = new GroovyClassLoader(cl);

        String grvyClassConcent = getScriptContent();
        Class groovyClass = grvyCl.parseClass(grvyClassConcent);
        try {
            GroovyObject grvyObj = (GroovyObject) groovyClass.newInstance();
            System.out.println("addStr=" + grvyObj.invokeMethod("addStr", map));

            Object[] argValues = new Object[]{"2017-12-08 08:00:00","yyyy-MM-dd hh:mm:ss","yyyyy/MM/dd  hh:mm:ss"};

            System.out.println("dateFormatterChange=" + grvyObj.invokeMethod("dateFormatterChange", argValues));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String getScriptContent() throws IOException {
        //将上面新建的Calculaotr.groovy类的代码放到grvyCalculatorFile的本地文件中
        InputStream fileInputStream = Application.class.getClassLoader().getResourceAsStream("script.txt");
        byte[] buf = new byte[1024];
        int length = 0;
        //循环读取文件内容，输入流中将最多buf.length个字节的数据读入一个buf数组中,返回类型是读取到的字节数。
        //当文件读取到结尾时返回 -1,循环结束。
        StringBuilder sb = new StringBuilder();
        while((length = fileInputStream.read(buf)) != -1){
            sb.append(new String(buf,0,length));
        }
        return sb.toString();
    }

    private static void way_one() {

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "a");
        map.put(1, "b");
        map.put(2, "c");

        GrvyCalculator calculator = new GrvyCalculator();
        System.out.println("discount=" + calculator.discount(10, 0.8));
        System.out.println("add=" + calculator.add(1,2));
        System.out.println("addStr=" + calculator.addStr(map));
    }
}
