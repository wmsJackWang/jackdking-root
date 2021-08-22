package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

public class GroupByConditiontTest {

    /**
     * @Description: TODO  对流的每个元素  按照某些条件进行分组，得到 map数据结构
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:成年人 value:[User(username=john1, age=24, gender=man), User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john7, age=67, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     * key:未成年人 value:[User(username=jack, age=12, gender=man), User(username=john4, age=12, gender=man), User(username=john4, age=12, gender=man)]
     **/
    @Test
    public void testGroupByConditiont() {
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("man").build())
                .build();

        Map map = userList.stream().collect(Collectors.groupingBy(item -> {
            if (item.getAge() < 18) {
                return "未成年人";
            } else {
                return "成年人";
            }
        }));

        map.forEach((k, v) -> System.out.println("key:" + k + " value:" + v));

    }
}
