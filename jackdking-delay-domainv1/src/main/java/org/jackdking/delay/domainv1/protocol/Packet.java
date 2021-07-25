package org.jackdking.delay.domainv1.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jackdking.delay.domainv1.protocol.command.Command;

@Data
public abstract class Packet{
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
