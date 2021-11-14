package org.jackdking.delay.domainv1.core.hook;

import io.netty.channel.Channel;
import org.jackdking.delay.domainv1.client.console.ConsoleCommandManager;
import org.jackdking.delay.domainv1.client.console.LoginConsoleCommand;
import org.jackdking.delay.domainv1.infrastructure.util.SessionUtil;

import java.util.Scanner;

/**
 * @Description: TODO command type to login
 * @MethodName: 
 * @Param: 
 * @return: 
 * @Author: jackdking
 * @User: 10421
 * @Date: 2021/11/14
 **/ 
public class ConnectionTryLoginHook implements ConnectionHook<Channel>{
    @Override
    public void hookConnectionEvent(Channel channel) {

        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    //not login channel, enter username and password to login
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    //has login channel, enter other command to do business
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();

    }
}
