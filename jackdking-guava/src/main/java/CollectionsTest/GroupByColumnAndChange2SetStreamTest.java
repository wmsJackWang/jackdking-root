package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupByColumnAndChange2SetStreamTest {

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
