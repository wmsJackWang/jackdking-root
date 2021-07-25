package org.jackdking.delay.domainv1.protocol.response;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;
import static org.jackdking.delay.domainv1.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGIN_RESPONSE;
    }
}
