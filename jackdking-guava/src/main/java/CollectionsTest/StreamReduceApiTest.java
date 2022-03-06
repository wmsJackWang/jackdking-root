package CollectionsTest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamReduceApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 12:05 下午
 * @Version 2.0
 **/
public class StreamReduceApiTest {

    @Test
    public void testStreamReduceApi() {
        //经过测试，当元素个数小于24时，并行时线程数等于元素个数，当大于等于24时，并行时线程数为16
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);

        //第一个参数x1为0, 后续就是上一次运算的结果， 而x2一直都是流的元素
        Integer v = list.stream().reduce((x1, x2) -> x1 + x2).get();
        System.out.println(v);   // 300

        //第一个参数x1为0, 后续就是上一次运算的结果， 而x2一直都是流的元素
        //且identity为x1初始值
        Integer v1 = list.stream().reduce(10, (x1, x2) -> x1 + x2);
        System.out.println(v1);  //310

        //accumulator跟上面一样，处理流元素， 而combiner是合并 拆分后子流的结果
        //在这里对于 没有拆分子流的 stream就不会执行combiner了，而是在parallelStream下执行
        Integer v2 = list.stream().reduce(0,
                (x1, x2) -> {
                    System.out.println("stream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("stream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        System.out.println(v2); // -300

        Integer v3 = list.parallelStream().reduce(0,
                (x1, x2) -> {
                    System.out.println("parallelStream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("parallelStream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        System.out.println(v3); //197474048
    }
}
