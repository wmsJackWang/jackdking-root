package CollectionsTest;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;


/*
 * 对stream的所有元素， 按照某个属性进行统计，得到key为属性，value为元素个数的结果
 */
@Slf4j
public class CountEleCountOfListTest {


    @Test
    public void testList2MapAndThenCount(){
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

        //统计流中  满足条件的元素个数
        userList.stream()
                .filter(user -> user.getAge()>14)
                .collect(Collectors.groupingBy(User::getUsername, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k+":"+v));
    }
}
