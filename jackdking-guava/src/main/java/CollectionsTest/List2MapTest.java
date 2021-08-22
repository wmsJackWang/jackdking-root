package CollectionsTest;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * list转map有两种方式
 * 1. Maps.uniqueIndex   key为字段，value为流元素
 * 2. stream.collect(Collector.toMap)   key 和 value跟流元素相关
 * 
 * 
 */
@Slf4j
public class List2MapTest {

    /**
     * @Description: TODO
     * @MethodName: 
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     *
     * john4:12
     * john3:46
     * john6:45
     * john5:23
     * john8:56
     * john7:67
     * john2:34
     * jack:12
     * john1:24
     *
     *
     * 1.常见业务场景list数据流的裁剪：查出db数据list，转成前端展示的数据list；查出的db数据list，但是只需要各元素对象中一种属性来做后续的业务。
     * 2.固定思维的处理流程：1. 先定义新的list 2. 然后for循环老数据依次对每个元素进行处理，3. 循环体中定义新的变量，计算得到值并添加到新的list中。
     * 3.用stream风格代码，1. 省去了繁琐的变量定义；2. 减少了因个人而导致的bug，因为都使用了stream规范编程；3. 更高的通用性【代码段维度的通用语言】， 减少了阅读代码成本。
     *
     **/
    @Test
    public void testList2Map() {
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .build();

        userList.stream()
                .collect(Collectors.toMap(user -> user.getUsername() , user -> user.getAge()))
                .forEach((k,v) -> System.out.println(k+":"+v));

    }

    @Test
    public void testStreamSorted(){
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("woman").build())
                .add(User.builder().username("katy").age(28).gender("man").build())
                .add(User.builder().username("jimi").age(2).gender("man").build())
                .build();

        List<User> result = userList.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .collect(Collectors.toList());

        result.stream().forEach(System.out::println);

        log.info("result :{}", JSON.toJSONString(result));
    }

}