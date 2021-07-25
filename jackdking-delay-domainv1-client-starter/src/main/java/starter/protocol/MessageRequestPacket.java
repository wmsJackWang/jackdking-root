package org.jackdking.delay.domainv1.ptotocol;

public class MessageRequestPacket implements Packet{

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
