package org.jackdking.delay.domainv1.ptotocol;

public class MessageResponsePacket implements Packet{
    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
