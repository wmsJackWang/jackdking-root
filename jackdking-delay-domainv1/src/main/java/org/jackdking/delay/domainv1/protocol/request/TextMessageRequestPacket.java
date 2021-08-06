package org.jackdking.delay.domainv1.protocol.request;


import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;
import static org.jackdking.delay.domainv1.protocol.command.Command.TEXT_MESSAGE_REQUEST;

@Data
public class TextMessageRequestPacket extends Packet {

    private String topic;

    private String producerId;

    private String content;

    @Override
    public Byte getCommand () {

        return TEXT_MESSAGE_REQUEST;
    }
}