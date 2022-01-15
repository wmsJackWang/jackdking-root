package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamLimitApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 11:40 上午
 * @Version 2.0
 **/
public class StreamLimitApiTest {

    @Test
    public void testStreamLimitApi() {
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
                .add(User.builder().username("john7").age(100).gender("man").build())
                .build();

        userList.stream()
                .sorted(Comparator.comparingInt(User::getAge))
                .limit(3)
                .forEach(System.out::println);


        //流 元素按照大小倒序排列
        userList.stream()
                .sorted((v1, v2)-> v2.getAge() - v1.getAge())
                .limit(3)
                .forEach(System.out::println);

        userList.stream()
                .sorted(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o2.getAge() - o1.getAge();
                    }
                })
                .limit(3)
                .forEach(System.out::println);


        //Comparator.reverseOrder() 只用作基本类型元素的list排序
        userList.stream()
                .map(User::getAge)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .forEach(System.out::println);

    }

}
