package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamFlatMapApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 10:57 上午
 * @Version 2.0
 **/
public class StreamFlatMapApiTest {

    @Test
    public void testStreamFlatMapApi() {
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


        System.out.println("flatMapToInt");

        userList.stream()
                .flatMapToInt(user -> IntStream.of(user.getAge()))
                .forEach(value -> System.out.print(value+" "));


        System.out.println("flatMapToLong");

        userList.stream()
                .flatMapToLong(user -> LongStream.of(user.getAge()))
                .forEach(value -> System.out.print(value+" "));


        System.out.println("flatMapToDouble");

        userList.stream()
                .flatMapToDouble(user -> DoubleStream.of(user.getAge()))
                .forEach(value -> System.out.print(value+" "));


    }
}
