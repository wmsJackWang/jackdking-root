package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

public class GroupByOneColumnList2MapTest {

    /**
     * @Description: 对象流 按照对象的某一个字段进行 分组  得到map数据
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * key:34  value:[User(username=john2, age=34, gender=man)]
     * key:67  value:[User(username=john7, age=67, gender=man)]
     * key:23  value:[User(username=john5, age=23, gender=man)]
     * key:56  value:[User(username=john8, age=56, gender=man)]
     * key:24  value:[User(username=john1, age=24, gender=man)]
     * key:12  value:[User(username=jack, age=12, gender=man), User(username=john4, age=12, gender=man)]
     * key:45  value:[User(username=john6, age=45, gender=man)]
     * key:46  value:[User(username=john3, age=46, gender=man)]
     *
     **/
    @Test
    public void testGroupByOneColumnList2Map() {

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

        Map map = userList.stream().collect(Collectors.groupingBy(User::getAge));

        map.forEach((k, v) -> {
            System.out.println("key:"+ k + "  value:" + v);
        });
    }

}
