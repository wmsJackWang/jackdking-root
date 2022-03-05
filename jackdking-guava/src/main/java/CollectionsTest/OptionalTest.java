package CollectionsTest;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Optional;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName OptionalTest
 * @Description TODO
 * @Author jackdking
 * @Date 20/08/2021 5:57 下午
 * @Version 2.0
 **/
@Slf4j
public class OptionalTest {

    /**
     * description: 测试结论：
     * 1. 用of方法，参数为空必报空指针异常；
     * 2. ofNullable方法 不会报错
     * 3. ofNullable经常结合orElse一起使用。
     * version: 1.0 <br>
     * date: 20/08/2021 6:06 下午 <br>
     * author: jackdking <br>
     *
     * @param : null
     * @return: null
     **/
    @Test
    public void testOptionalOfNullable() {
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
        Optional.ofNullable(userList).orElse(ImmutableList.of()).stream().forEach(System.out::println);
    }

    @Test
    public void testOptionIfPersent() {
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

        Optional.ofNullable(userList.get(2))
                .ifPresent(System.out::println);

    }

    @Test
    public void testOptionalOf() {
        User user = null;
        Optional.of(user).get();

    }

    @Test
    public void testOptionFilter() {
        User user = User.builder().username("john8").age(56).gender("man").build();
        System.out.println("第一个条件过滤结果：");
        Optional.of(user).filter(user1 -> user1.getUsername()=="john8").ifPresent(System.out::println);
        System.out.println("第一个条件过滤筛选：");
        Optional.of(user).filter(user1 -> user1.getUsername()=="jack").ifPresent(System.out::println);
    }


}
