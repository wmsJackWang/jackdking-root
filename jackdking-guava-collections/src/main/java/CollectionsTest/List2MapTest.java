package CollectionsTest;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * list转map有两种方式
 * 1. Maps.uniqueIndex   key为字段，value为流元素
 * 2. stream.collect(Collector.toMap)   key 和 value跟流元素相关
 */
@Slf4j
public class List2MapTest {

    @Test
    public void testList2Map_uniqueIndex_1(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("man").build())
                .build();
        Map result = Maps.uniqueIndex(userList, new Function<User, Object>() {

            @Override
            public @Nullable Object apply(@Nullable User user) {
                return user.getUsername();
            }
        });
        log.info("result :{}", JSON.toJSONString(result));
    }

    @Test
    public void testList2Map_uniqueIndex_2() {

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("man").build())
                .build();

        Iterator<User> userIterator = userList.iterator();

        Map result = Maps.uniqueIndex(userIterator, new Function<User, String>() {

            @Override
            public @Nullable String apply(@Nullable User user) {
                return user.getUsername();
            }
        });
        log.info("result :{}", JSON.toJSONString(result));
    }

    @Test
    public void testList2Map() {
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

        userList.stream()
                .collect(Collectors.toMap(user -> user.getUsername() , user -> user.getAge()))
                .forEach((k,v) -> System.out.println(k+":"+v));

    }

    @Test
    public void testStreamSorted(){
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("woman").build())
                .add(User.builder().username("katy").age(28).gender("man").build())
                .add(User.builder().username("jimi").age(2).gender("man").build())
                .build();

        List<User> result = userList.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .collect(Collectors.toList());

        result.stream().forEach(System.out::println);

        log.info("result :{}", JSON.toJSONString(result));
    }

}

@Data
@Builder
class User {
    String username;
    Integer age;
    String gender;
    BigDecimal balance;
}