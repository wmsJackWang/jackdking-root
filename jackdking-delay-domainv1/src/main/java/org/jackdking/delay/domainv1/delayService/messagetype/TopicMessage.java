package org.jackdking.delay.domainv1.delayService.messagetype;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicMessage <T> extends DelayMessage<T> {


    private String productId;

    private String topic;

    public T playload;

    public long expireTime;

    public String uniqueKey;

    public String bizType;

    @Override
    public String getMessageJson() {
        return null;
    }

}
