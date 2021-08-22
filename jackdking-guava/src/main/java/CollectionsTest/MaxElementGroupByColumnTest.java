package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MaxElementGroupByColumnTest {


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

}
