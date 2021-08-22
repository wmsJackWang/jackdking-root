package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class List2MapAndThenSumColumnTest {


    /**
     * @Description: TODO 计算流的 按照某属性分类  然后统计某个属性值的总和
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:woman [年龄总和]value:91
     * key:man [年龄总和]value:228
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
}
