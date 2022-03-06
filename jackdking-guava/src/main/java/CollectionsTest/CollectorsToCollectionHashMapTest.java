package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsToCollectionHashMapTest {


    /**
     * @Description: TODO  展示了 将对象流转化为 list 和 set两种流。
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     * 最后一个jack对象覆盖了 前面的jack对象
     * key:  value:User(username=john2, age=34, gender=man, balance=null)
     * key:  value:User(username=jack, age=46, gender=woman, balance=null)
     * key:  value:User(username=john1, age=24, gender=man, balance=null)
     **/
    @Test
    public void testCollectorsToMap2HashMap(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("jack").age(46).gender("woman").build())
                .build();

        Map map = userList.parallelStream().collect(Collectors.toMap(User::getUsername, Function.identity(), (newMap, oldMap) -> {
            return oldMap;
        }));

        map.forEach((k, v) ->  System.out.println("key: " + k + " value:"+v));
    }
}
