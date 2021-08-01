package org.jackdking.delay.domainv1.protocol.response;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;
import static org.jackdking.delay.domainv1.protocol.command.Command.TOPIC_MANAGE_RESPONSE;

@Data
public class TopicManageResponsePacket  extends Packet {

    private String topicName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return TOPIC_MANAGE_RESPONSE;
    }
}
