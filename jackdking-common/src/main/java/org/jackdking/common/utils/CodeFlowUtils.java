package org.jackdking.common.utils;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName CodeFlowUtils
 * @Description TODO
 * @Author jackdking
 * @Date 26/11/2021 4:37 下午
 * @Version 2.0
 **/
public class CodeFlowUtils {

    private static final Pattern EXPRESSION_PATTERN_V2 = Pattern.compile("\\$\\{(.+?)\\}");

    public static <T> void acceptIf(T value, Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(value)) {
            consumer.accept(value);
        }
    }

    public static <T> void acceptIfElse(T value, Predicate<T> predicate, Consumer<T> trueConsumer, Consumer<T> falseConsumer) {
        if (predicate.test(value)) {
            trueConsumer.accept(value);
        } else {
            falseConsumer.accept(value);
        }
    }

    public static <T, R> R acceptIfElse(T value, Predicate<T> predicate, Function<T, R> trueFunction, Function<T, R> falseFunction) {
        if (predicate.test(value)) {
            return trueFunction.apply(value);
        } else {
            return falseFunction.apply(value);
        }
    }

    public static String parse(String content, Map<String, Object> data) {
        Pattern p = EXPRESSION_PATTERN_V2;
        Matcher m = p.matcher(content);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String key = m.group(1);
            String value = String.valueOf((data.get(key)));
            m.appendReplacement(sb, value == null ? "" : value);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
