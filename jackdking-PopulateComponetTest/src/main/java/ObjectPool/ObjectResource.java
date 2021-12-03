package ObjectPool;

import lombok.ToString;
import org.apache.logging.log4j.util.Strings;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName ObjectResource
 * @Description TODO
 * @Author jackdking
 * @Date 15/11/2021 1:52 下午
 * @Version 2.0
 **/
@ToString
public class ObjectResource {

    private static final AtomicInteger base = new AtomicInteger(0);
    String name = base.getAndAdd(3) + Strings.EMPTY;

    @Override
    public String toString() {
        return "ObjectResource{" +
                "name='" + name + '\'' +
                '}';
    }
}
