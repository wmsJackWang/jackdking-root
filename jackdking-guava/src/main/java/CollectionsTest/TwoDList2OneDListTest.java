package CollectionsTest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ObjectArrays;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName TwoDList2OneDListTest
 * @Description TODO
 * @Author jackdking
 * @Date 03/12/2021 12:00 下午
 * @Version 2.0
 **/
public class TwoDList2OneDListTest {

    @Test
    public void testTwoDList2OneDList(){
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

        //二维list结构转化为  一维的list结构
        //
        userList.stream()
                .map(user -> Lists.newArrayList(user.getAge().toString(), user.getUsername(), user.getGender()))
                .flatMap(Collection::stream)
                .forEach(System.out::println);
    }

    @Test
    public void testTwoDArray2OneDArrayOfString(){

        String[] array1 = new String[]{"12", "34", "56"};
        String[] array2 = new String[]{"121", "342", "563"};


        List<String[]> list = Lists.newArrayList(array1, array2);

        System.out.println("Stream::of 将数组转化为流");
        list.stream()
                .flatMap(Stream::of)
                .collect(Collectors.toList())
                .forEach(System.out::println);


        System.out.println("Arrays::stream 将数组转化为流");
        list.stream()
                .flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }


    //结合Common-lang包的ArrayUtils使用，将
    @Test
    public void testTwoDArray2OneDArrayOfInt(){

        int[] array1 = new int[]{12, 34, 5};
        int[] array2 = new int[]{121, 342, 563};

        List<int[]> list = Lists.newArrayList(array1, array2);

        System.out.println("Stream::of 将数组转化为流");
        list.stream()
                .map(ArrayUtils::toObject)
                .flatMap(Stream::of)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("Arrays::stream 将数组转化为流");
        list.stream()
                .map(ArrayUtils::toObject)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }

}
