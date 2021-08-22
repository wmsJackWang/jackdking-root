package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

public class GroupByBeyondTwoColumnList2MapTest {

    /**
     * @Description: TODO  对象流按照 对象的多个字段进行分组 得到 map数据结构
     *                  按照用户对象的用户名和年龄进行分组
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:john8_56  value:[User(username=john8, age=56, gender=man)]
     * key:john7_67  value:[User(username=john7, age=67, gender=man), User(username=john7, age=67, gender=man)]
     * key:john6_45  value:[User(username=john6, age=45, gender=man)]
     * key:john1_24  value:[User(username=john1, age=24, gender=man)]
     * key:john3_46  value:[User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man)]
     * key:john2_34  value:[User(username=john2, age=34, gender=man)]
     * key:john4_12  value:[User(username=john4, age=12, gender=man), User(username=john4, age=12, gender=man)]
     * key:john5_23  value:[User(username=john5, age=23, gender=man)]
     * key:jack_12  value:[User(username=jack, age=12, gender=man)]
     **/
    @Test
    public void testGroupByBeyondTwoColumnList2Map() {

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

        Map map = userList.stream().collect(Collectors.groupingBy(item -> item.getUsername()+"_"+item.getAge()));

        map.forEach((k, v) -> {
            System.out.println("key:"+ k + "  value:" + v);
        });

    }

}
