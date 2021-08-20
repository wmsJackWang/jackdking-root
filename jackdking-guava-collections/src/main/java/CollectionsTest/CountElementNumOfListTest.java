package CollectionsTest;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/*
 * 对stream的所有元素， 按照某个属性进行统计，得到key为属性，value为元素个数的结果
 */
@Slf4j
public class CountElementNumOfListTest {

    /**
     * @Description: TODO 按照分组进行统计，得到每组的统计数据
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * woman:1
     * man:6
     *
     **/ 
    @Test
    public void testList2MapAndThenCount(){
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("woman").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("woman").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("woman").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .build();

        //统计流中  满足条件的元素个数
        userList.stream()
                .filter(user -> user.getAge()>14)
                .collect(Collectors.groupingBy(User::getGender, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k+":"+v));
    }

    /**
     * @Description: TODO 计算流的 按照某属性分类  然后统计某个属性值的总和
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     **/ 
    @Test
    public void testList2MapAndThenSumColumn() {

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("woman").balance(new BigDecimal(1.1)).build())
                .add(User.builder().username("john1").age(24).gender("man").balance(new BigDecimal(1.3)).build())
                .add(User.builder().username("john2").age(34).gender("man").balance(new BigDecimal(1.14)).build())
                .add(User.builder().username("john3").age(46).gender("man").balance(new BigDecimal(1.5)).build())
                .add(User.builder().username("john4").age(12).gender("woman").balance(new BigDecimal(6.1)).build())
                .add(User.builder().username("john5").age(23).gender("man").balance(new BigDecimal(7.1)).build())
                .add(User.builder().username("john6").age(45).gender("man").balance(new BigDecimal(4.1)).build())
                .add(User.builder().username("john7").age(67).gender("woman").balance(new BigDecimal(8.1)).build())
                .add(User.builder().username("john8").age(56).gender("man").balance(new BigDecimal(1.9)).build())
                .build();

        Map<String, LongSummaryStatistics> map = userList.stream().collect(Collectors.groupingBy(User::getGender, Collectors.summarizingLong(User::getAge)));

        map.forEach((k, v) -> {
            System.out.println("key:"+k+" [年龄总和]value:"+v.getSum());
        });
    }

    /**
     * @Description: TODO 对流 按照性别进行分类，然后计算得到每个分类下 年龄最大的用户对象
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:woman [最大年龄]value:67
     * key:man [最大年龄]value:56
     **/ 
    @Test
    public void testMaxElementGroupByColumn() {

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("woman").balance(new BigDecimal(1.1)).build())
                .add(User.builder().username("john1").age(24).gender("man").balance(new BigDecimal(1.3)).build())
                .add(User.builder().username("john2").age(34).gender("man").balance(new BigDecimal(1.14)).build())
                .add(User.builder().username("john3").age(46).gender("man").balance(new BigDecimal(1.5)).build())
                .add(User.builder().username("john4").age(12).gender("woman").balance(new BigDecimal(6.1)).build())
                .add(User.builder().username("john5").age(23).gender("man").balance(new BigDecimal(7.1)).build())
                .add(User.builder().username("john6").age(45).gender("man").balance(new BigDecimal(4.1)).build())
                .add(User.builder().username("john7").age(67).gender("woman").balance(new BigDecimal(8.1)).build())
                .add(User.builder().username("john8").age(56).gender("man").balance(new BigDecimal(1.9)).build())
                .build();

        Map<String, User> map = userList.stream()
                .collect(Collectors.groupingBy(User::getGender,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(User::getAge)), Optional::get)
                ));
        map.forEach((k, v) -> {
            System.out.println("key:"+k+" [最大年龄]value:"+v.getAge());
        });

    }

    /**
     * @Description: TODO  对流进行处理， 按照某个字段分组，然后将分组的流转化成其他类型的流
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:woman, value: [john4, john7, jack]
     * key:man, value: [john3, john6, john5, john8, john2, john1]
     **/ 
    @Test
    public void testGroupByColumnAndChange2SetStream() {


        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("woman").balance(new BigDecimal(1.1)).build())
                .add(User.builder().username("john1").age(24).gender("man").balance(new BigDecimal(1.3)).build())
                .add(User.builder().username("john2").age(34).gender("man").balance(new BigDecimal(1.14)).build())
                .add(User.builder().username("john3").age(46).gender("man").balance(new BigDecimal(1.5)).build())
                .add(User.builder().username("john4").age(12).gender("woman").balance(new BigDecimal(6.1)).build())
                .add(User.builder().username("john5").age(23).gender("man").balance(new BigDecimal(7.1)).build())
                .add(User.builder().username("john6").age(45).gender("man").balance(new BigDecimal(4.1)).build())
                .add(User.builder().username("john7").age(67).gender("woman").balance(new BigDecimal(8.1)).build())
                .add(User.builder().username("john8").age(56).gender("man").balance(new BigDecimal(1.9)).build())
                .build();

        Map<String, Set<String>> map = userList.stream().collect(Collectors.groupingBy(User::getGender, Collectors.mapping(User::getUsername, Collectors.toSet())));

        map.forEach((k, v) -> {
            System.out.println("key:" + k + ", value: " +v);
        });
    }

}
