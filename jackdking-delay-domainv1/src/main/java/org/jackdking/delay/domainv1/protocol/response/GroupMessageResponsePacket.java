package org.jackdking.delay.domainv1.protocol.response;

import lombok.Data;
import org.jackdking.delay.domainv1.infrastructure.session.Session;
import org.jackdking.delay.domainv1.protocol.Packet;

import static org.jackdking.delay.domainv1.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
