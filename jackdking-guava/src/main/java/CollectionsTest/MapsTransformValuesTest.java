package CollectionsTest;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MapsTransformValuesTest {

    @Test
    public void testTransformValues () {
        Map<String, Object> map = ImmutableMap.of("biz1", "12", "biz2", "34", "biz3", "9");

        Map<String, Long> result = Maps.transformValues(map, new Function<Object, Long>() {
            @Override
            public @Nullable Long apply(@Nullable Object s) {
                return Long.valueOf(s.toString());
            }
        });
        System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(result));
    }

}
