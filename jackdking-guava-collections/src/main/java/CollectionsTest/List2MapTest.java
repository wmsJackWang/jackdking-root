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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Map result = Maps.uniqueIndex(userIterator, new Function<User, Object>() {

            @Override
            public @Nullable Object apply(@Nullable User user) {
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
    public void testList2MapAndThenCount(){
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

        //统计流中  满足条件的元素个数
        userList.stream()
                .filter(user -> user.getAge()>14)
                .collect(Collectors.groupingBy(User::getUsername, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k+":"+v));
    }

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


    //将流中的元素映射到另一个流中
    @Test
    public void testStreamAMap2StreamB(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("man").build())
                .build();

        Stream<Integer> userAges = userList.stream().map(user -> user.getAge());
        Stream<String>  userNames = userList.stream().map(user -> user.getUsername());

        log.info("userAges :{}", JSON.toJSONString(userAges));
        log.info("userNames :{}", JSON.toJSONString(userNames));
    }


    //将流中的每个元素都各自映射到一个流中，然后flatmap 将各个流合并, 需要结合map方法使用
    //Arrays::stream , Stream::of
    @Test
    public void testStreamAFlatMap2StreamB(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john").age(21).gender("man").build())
                .build();

        userList.stream()
                .map(user -> user.getUsername())
                .map(userName -> userName.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);

        userList.stream()
                .map(user -> user.getGender())
                .map(gender -> gender.split(""))
                .distinct()
                .flatMap(Stream::of)
                .forEach(System.out::print);

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
}