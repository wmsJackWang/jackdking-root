package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;

import static org.jackdking.delay.domainv1.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
