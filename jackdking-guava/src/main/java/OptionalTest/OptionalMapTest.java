package OptionalTest;


import CollectionsTest.User;
import lombok.Builder;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import sun.lwawt.macosx.CSystemTray;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalMapTest {
    @Test
    public void testOptionalMap() {

        User user = User.builder().username("jack").age(18).gender("man").build();

        String username = Optional.of(user).map(User::getUsername).orElse("nobody");
        System.out.println("username:" + username);
    }

  @Data
  public static class DrunkOrderDetectStatisticalFeatureDO {

    private String tag;

    private String value;

    public String getTag() {
      return tag;
    }

    public String getValue() {
      return value;
    }
  }
    @Test
    public void testOpt() {
      Integer res = 0 | 0;
      System.out.println(res);

      List<DrunkOrderDetectStatisticalFeatureDO> list = Lists.newArrayList();

      Map<String, DrunkOrderDetectStatisticalFeatureDO>  map = list.stream().collect(Collectors.toMap(item -> String.format("%s-%s", item.getTag(), item.getValue()), entry -> entry, (k1, k2) -> k1));
      System.out.println(map.get(""));
    }
}




