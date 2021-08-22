package CollectionsTest;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectorsToCollectionArrayListAndHashSetTest {

    /**
     * @Description: TODO  展示了 将对象流转化为 list 和 set两种流。
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/8/22
     * oldUsernameList:[jack, john1, john2, jack]
     * newUsernameSet:[jack, john2, john1]
     **/
    @Test
    public void testCollectorsToCollectionArrayListAndHashSet(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("jack").age(12).gender("man").build())
                .add(User.builder().username("john1").age(24).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("jack").age(46).gender("man").build())
                .build();

        List<String> oldUsernameList = userList.parallelStream().map(User::getUsername).collect(Collectors.toCollection(ArrayList::new));
        Set<String> newUsernameSet = userList.parallelStream().map(User::getUsername).collect(Collectors.toCollection(HashSet::new));

        System.out.println("oldUsernameList:"+oldUsernameList);
        System.out.println("newUsernameSet:"+newUsernameSet);
    }
}
