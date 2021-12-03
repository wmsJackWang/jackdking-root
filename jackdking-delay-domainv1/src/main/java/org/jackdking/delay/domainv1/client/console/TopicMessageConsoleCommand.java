package org.jackdking.delay.domainv1.client.console;

import io.netty.channel.Channel;
import org.jackdking.delay.domainv1.protocol.request.TextMessageRequestPacket;

import java.util.Scanner;

public class TopicMessageConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {

        TextMessageRequestPacket textMessageRequestPacket = new TextMessageRequestPacket();

        System.out.print("输入 producerId：");
        textMessageRequestPacket.setProducerId(scanner.nextLine());
        System.out.print("输入 topic：");
        textMessageRequestPacket.setTopic(scanner.nextLine());
        System.out.print("输入 message：");
        textMessageRequestPacket.setContent(scanner.nextLine());
        System.out.print("输入 多久后消息过期：");
        textMessageRequestPacket.setExpireTime(Long.valueOf(scanner.nextLine()));

        channel.writeAndFlush(textMessageRequestPacket);
    }
}
