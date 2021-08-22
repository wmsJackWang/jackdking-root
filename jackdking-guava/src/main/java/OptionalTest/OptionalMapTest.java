package OptionalTest;


import CollectionsTest.User;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class OptionalMapTest {


    @Test
    public void testOptionalMap() {

        User user = User.builder().username("jack").age(18).gender("man").build();

        String username = Optional.of(user).map(User::getUsername).orElse("nobody");
        System.out.println("username:" + username);
    }
}

