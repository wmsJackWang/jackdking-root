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

        ageUsers.forEach((k, v) -> System.out.println("age:"+k.intValue() + ",value:" + JSON.toJSONString(v)));

    }

}
