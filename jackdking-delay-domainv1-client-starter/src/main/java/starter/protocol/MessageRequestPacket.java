package starter.protocol;

import lombok.Data;

@Data
public class MessageRequestPacket implements Packet{

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
