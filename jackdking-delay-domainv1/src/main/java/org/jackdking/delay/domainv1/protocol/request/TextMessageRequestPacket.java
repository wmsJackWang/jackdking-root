package org.jackdking.delay.domainv1.protocol.request;


import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;

import java.util.concurrent.TimeUnit;

import static org.jackdking.delay.domainv1.protocol.command.Command.TEXT_MESSAGE_REQUEST;

@Data
public class TextMessageRequestPacket extends Packet {

    private String topic;

    private String producerId;

    private String content;

    private long expireTime;

    private TimeUnit timeType;

    @Override
    public Byte getCommand () {

        return TEXT_MESSAGE_REQUEST;
    }
}