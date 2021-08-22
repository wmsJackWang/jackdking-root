package CollectionsTest;

import com.google.common.collect.ImmutableList;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Param;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName GroupList2MapTest
 * @Description 对流进行分组，按照流中元素的字段来分类，得到分类map数据
 * @Author jackdking
 * @Date 20/08/2021 6:10 下午
 * @Version 2.0
 **/
@Slf4j
public class MultiLevelGroupByColumn2MapTest {

    /**
     * @Description: TODO   多级分组，将流对象按照 属性或者条件 进行多级分组 ， 可以一直继续分下去
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/21
     *
     * woman
     *   未成年人
     *     [User(username=john4, age=12, gender=woman)]
     *   成年人
     *     [User(username=john1, age=24, gender=woman), User(username=john7, age=67, gender=woman)]
     *     [User(username=john1, age=24, gender=woman), User(username=john7, age=67, gender=woman)]
     * man
     *   成年人
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *     [User(username=john2, age=34, gender=man), User(username=john3, age=46, gender=man), User(username=john3, age=46, gender=man), User(username=john5, age=23, gender=man), User(username=john6, age=45, gender=man), User(username=john8, age=56, gender=man), User(username=john7, age=67, gender=man)]
     *   未成年人
     *     [User(username=jack, age=12, gender=man), User(username=john4, age=12, gender=man)]
     *     [User(username=jack, age=12, gender=man), User(username=john4, age=12, gender=man)]
     *
     **/ 
    @Test
    public void testMultiLevelGroupByColumn() {


        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("woman").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("woman").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("woman").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .add(User.builder().username("john7").age(67).gender("man").build())
                .build();

        Map<String, Map<String, List<User>>> map = userList.stream().collect(Collectors.groupingBy(User::getGender ,
                Collectors.groupingBy(item -> {
                    if (item.getAge() < 18) {
                        return "未成年人";
                    } else {
                        return "成年人";
                    }
                })));

        map.forEach((k, v) -> {
            System.out.println(k);
            v.forEach((k1, v2) -> {
                System.out.println("  " + k1);
                v2.stream().forEach(item -> System.out.println("    "+v2));
            });
        });
    }

}
