package CollectionsTest;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class List2Map_uniqueIndexTest {

    /**
     * @Description: TODO  类似groupBy功能， 但是这个可以是对元素的属性进行计算得到唯一的key。
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     *
     *
     * result :{"jack":{"age":12,"gender":"man","username":"jack"},"john":{"age":21,"gender":"man","username":"john"}}
     **/ 
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
        log.info("\nresult :{}", JSON.toJSONString(result));
    }

    /**
     * @Description: TODO
     * @MethodName:
     * @Param: 
     * @return: 
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     * result :{"jack":{"age":12,"gender":"man","username":"jack"},"john":{"age":21,"gender":"man","username":"john"}}
     **/ 
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

}
