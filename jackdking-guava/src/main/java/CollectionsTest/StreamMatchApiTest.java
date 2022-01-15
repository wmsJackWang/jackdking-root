package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName StreamMatchApiTest
 * @Description TODO
 * @Author jackdking
 * @Date 06/12/2021 10:31 上午
 * @Version 2.0
 **/
public class StreamMatchApiTest {

    @Test
    public void testStreamMatchApi() {
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

        //nonMatch是, 希望所有元素都不满足传入的条件，则为true，否则false
        boolean isMatch = userList.stream()
                .noneMatch(user -> user.getAge()>=100);
        if (isMatch) {
            System.out.println("没有超过100岁的人");
        } else {
            System.out.println("有超过100岁的人");
        }

        isMatch = userList.stream()
                .anyMatch(user -> user.getAge() == 12);
        if (isMatch) {
            System.out.println("有等于12岁的人");
        } else {
            System.out.println("没有等于12岁的人");
        }
        String man = new String("man");
        isMatch = userList.stream()
                .allMatch(user -> user.getGender().equals(man));
        if (isMatch) {
            System.out.println("所有人都是man");
        } else {
            System.out.println("不是所有人都是man");
        }

    }
}
