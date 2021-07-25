package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;

import static org.jackdking.delay.domainv1.protocol.command.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
