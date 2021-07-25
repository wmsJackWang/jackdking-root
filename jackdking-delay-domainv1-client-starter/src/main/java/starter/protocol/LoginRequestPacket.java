package starter.protocol;

import lombok.Data;

@Data
public class LoginRequestPacket {
    String username;
    String password;
}
