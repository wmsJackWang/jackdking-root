package CollectionsTest;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TestJoiner {

    @Test
    public void testJoiner() {
        Map<String, Object> map = ImmutableMap.of("biz1", "12", "biz2", "34", "biz3", "9");
        System.out.println(Joiner.on(",").withKeyValueSeparator('=').join(map));

    }
}
