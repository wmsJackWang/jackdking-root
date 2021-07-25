package org.jackdking.delay.domainv1.infrastructure.serialize.impl;

import com.alibaba.fastjson.JSON;
import org.jackdking.delay.domainv1.infrastructure.serialize.Serializer;
import org.jackdking.delay.domainv1.infrastructure.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
