package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jackdking.delay.domainv1.protocol.Packet;


import static org.jackdking.delay.domainv1.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
