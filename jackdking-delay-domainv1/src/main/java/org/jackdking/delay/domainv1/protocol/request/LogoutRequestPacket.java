package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;

import static org.jackdking.delay.domainv1.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
