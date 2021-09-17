package ace.core;

import ace.core.api.AttributorMatcher;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AceAttributorChecker
 * @Description TODO
 * @Author jackdking
 * @Date 17/09/2021 4:28 下午
 * @Version 2.0
 **/
public class AceAttributorCheckManager {

    public static boolean check(AceContext aceContext, AttributorMatcher matcher) {

        //common checker
        if(!matcher.defaultCheck(aceContext)) {
            return false;
        }

        //bussiness check
        if (!matcher.bizCheck(aceContext)) {
            return false;
        }

        return matcher.isMatch(aceContext.getDataParam(), aceContext.getRulerParam());
    }

    public static boolean check(AceContext aceContext, AttributorMatcher matcher, String... argKey){
        Preconditions.checkArgument(argKey.length <= 2147483635, "the total number of elements must fit in an int");
        Preconditions.checkArgument(aceContext.getDataParam() instanceof Map, "dataParam must be Map");
        Preconditions.checkArgument(((Map<?, ?>) aceContext.getDataParam()).keySet().stream().allMatch(key -> key instanceof String), "map key must be String");
        //没有需要null判断的参数
        if(argKey.length<=0) {
            return true;
        }
        if (Arrays.stream(argKey).allMatch(e -> Objects.nonNull(((Map<String, ?>) aceContext.getDataParam()).get(e)))) {
            return true;
        }

        return false;
    }
}
