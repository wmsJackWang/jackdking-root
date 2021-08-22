package CollectionsTest;


import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class StreamParallelTest {

    /*
     * stream.parallel 流  foreach
     * parallelStream  流  foreach
     * 异步多线程  处理流中的元素
     */
    @Test
    public void testParallelStream(){
        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .build();
        System.out.println("stream parallel  ");
        userList.stream().parallel().forEach(user -> {
            log.info("stream parallel :"+user.getUsername());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("parallelStream ");
        userList.parallelStream().forEach(user -> {
            log.info("parallelStream :"+user.getUsername());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("normal stream ");
        userList.stream().forEach(user -> {
            log.info("normal stream "+user.getUsername());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void testSynchronizedListParallelStream(){

        ImmutableList<User> userList = ImmutableList.<User>builder()
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john4").age(12).gender("man").build())
                .add(User.builder().username("john5").age(23).gender("man").build())
                .add(User.builder().username("john6").age(45).gender("man").build())
                .add(User.builder().username("john2").age(34).gender("man").build())
                .add(User.builder().username("john3").age(46).gender("man").build())
                .add(User.builder().username("john8").age(56).gender("man").build())
                .build();
        System.out.println("parallelStream ");

        List<User> synchronizedList = Collections.synchronizedList(userList);


        synchronizedList.parallelStream().forEach(user -> {
            log.info("parallelStream :"+user.getUsername() + "enter");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("parallelStream :"+user.getUsername() + "exist");
        });

    }

}
