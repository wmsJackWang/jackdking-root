package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamSkipApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 12:19 下午
 * @Version 2.0
 **/
public class StreamSkipApiTest {


    //skip(n),意义是将几个元素之前的所有元素去掉，包括该元素。
    //n是序号，不是索引。
    @Test
    public void testStreamSkipApi() {

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
                .skip(1)
                .forEach(System.out::println);

    }
}
