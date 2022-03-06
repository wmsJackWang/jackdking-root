package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamPeekApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 11:36 上午
 * @Version 2.0
 **/
public class StreamPeekApiTest {


    //peek只是消费，类似map，但是没有返回值得consumer
    @Test
    public void testStreamPeekApi() {
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
                .peek(user -> user.setAge(1000))
                .forEach(System.out::println);

    }
}
