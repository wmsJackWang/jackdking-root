package org.jackdking.delay.domainv1.ptotocol;

import lombok.Data;

@Data
public class LoginRequestPacket {
    String username;
    String password;
}
