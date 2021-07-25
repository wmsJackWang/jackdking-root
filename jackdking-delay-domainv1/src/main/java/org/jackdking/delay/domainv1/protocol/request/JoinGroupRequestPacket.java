package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;


import static org.jackdking.delay.domainv1.protocol.command.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_REQUEST;
    }
}
