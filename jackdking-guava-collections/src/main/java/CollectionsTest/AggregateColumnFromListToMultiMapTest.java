package CollectionsTest;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
 * 将流中的元素  按照某个字段 进行聚合，得到一个Map<column，List<element>>数据
 *
 * 聚合结果如下：
key:34,value:[{"age":34,"gender":"man","username":"john2"}]
key:67,value:[{"age":67,"gender":"man","username":"john7"}]
key:23,value:[{"age":23,"gender":"man","username":"john5"}]
key:56,value:[{"age":56,"gender":"man","username":"john8"}]
key:24,value:[{"age":24,"gender":"man","username":"john1"}]
key:12,value:[{"age":12,"gender":"man","username":"john4"},{"age":12,"gender":"man","username":"jack"}]
key:45,value:[{"age":45,"gender":"man","username":"john6"}]
key:46,value:[{"age":46,"gender":"man","username":"john3"}]

 */
@Slf4j
public class AggregateColumnFromListToMultiMapTest {

    @Test
    public void testList2MultiMap() {

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

        Map<Integer , List<User>> ageUsers = userList.stream()
                .collect(Collectors.toMap(user -> user.getAge(), user -> Lists.newArrayList(user), (List<User> newValueList, List<User> oldValueList) -> {
                    oldValueList.addAll(newValueList);
                    return oldValueList;
                }));

        ageUsers.forEach((k, v) -> System.out.println("key:"+k.intValue() + ",value:" + JSON.toJSONString(v)));

    }

}
