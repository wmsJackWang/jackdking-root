package CollectionsTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*
 * 对流中的每个元素进行处理，一般是获取部分数据，然后汇聚成新的stream
 * 这就是map方法的作用。
 */
@Slf4j
public class StreamAChangeToStreamBTest {


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
    //Arrays::stream , Stream::of, Stream.of本质是调用了Arrays.stream
    @Test
    public void testStreamAFlatMap2StreamB(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack.d.king").age(12).gender("man").build())
                .add(User.builder().username("john.d.tty").age(21).gender("man").build())
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
                .flatMap(Stream::of)//Stream.of方法将String[]字符串数组转化为字符串流，然后被flapMap融合
                                    //flatMap方法参数返回的值必须是一个stream

                .distinct()
                .forEach(System.out::println);

    }

}
