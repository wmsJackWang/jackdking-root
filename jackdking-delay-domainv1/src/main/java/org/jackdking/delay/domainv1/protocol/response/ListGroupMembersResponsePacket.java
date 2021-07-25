package org.jackdking.delay.domainv1.protocol.response;

import lombok.Data;
import org.jackdking.delay.domainv1.infrastructure.session.Session;
import org.jackdking.delay.domainv1.protocol.Packet;
import static org.jackdking.delay.domainv1.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

import java.util.List;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {

        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
