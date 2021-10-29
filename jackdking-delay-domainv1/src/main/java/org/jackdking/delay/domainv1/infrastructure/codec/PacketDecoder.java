package org.jackdking.delay.domainv1.infrastructure.codec;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jackdking.delay.domainv1.protocol.Packet;
import org.jackdking.delay.domainv1.protocol.PacketCodec;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {
        Packet packet = PacketCodec.INSTANCE.decode(in);
        Preconditions.checkNotNull(packet, "decode result packet cant be null");
        out.add(packet);
    }
}
