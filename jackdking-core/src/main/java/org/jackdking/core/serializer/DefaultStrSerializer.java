//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jackdking.core.serializer;

import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

public class DefaultStrSerializer implements RedisSerializer<Object> {
    private final Charset charset;

    public DefaultStrSerializer() {
        this(Charset.forName("UTF8"));
    }

    public DefaultStrSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    public byte[] serialize(Object o) throws SerializationException {
        return o == null ? null : String.valueOf(o).getBytes(this.charset);
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        return bytes == null ? null : new String(bytes, this.charset);
    }
}
