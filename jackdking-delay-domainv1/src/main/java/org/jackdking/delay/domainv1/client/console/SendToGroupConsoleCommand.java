package org.jackdking.delay.domainv1.client.console;

import io.netty.channel.Channel;
import org.jackdking.delay.domainv1.protocol.request.GroupMessageRequestPacket;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个群组：");

        String toGroupId = scanner.nextLine();
        String message = scanner.nextLine();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));

    }
}
