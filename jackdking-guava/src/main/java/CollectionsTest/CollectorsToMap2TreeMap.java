package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsToMap2TreeMap {

    /**
     * @Description: TODO 一下两种方式 计算同一个目标， 得到相同的结果  toMap 和 groupingBy两种方式
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     *
     * key: man  value: 216
     * key: woman  value: 103
     *
     *
     * key: woman  value: 103
     * key: man  value: 216
     **/ 
    @Test
    public void testCollectorsToMap2TreeMap() {


        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("woman").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("woman").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("woman").build())
                .build();

        TreeMap treeMap = userList.stream().collect(Collectors.toMap(User::getGender, User::getAge, (left , right) -> {
                    return left+right;
                } , TreeMap::new
                ));
        treeMap.forEach((k, v) -> System.out.println("key: "+k + "  value: "+v));


        userList.stream().collect(Collectors.groupingBy(User::getGender, Collectors.summingInt(User::getAge)))
        .forEach((k, v) -> System.out.println("key: "+k + "  value: "+v));

    }

}
