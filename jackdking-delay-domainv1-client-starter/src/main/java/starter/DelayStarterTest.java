package starter;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import starter.delayclient.NettyClient;
import starter.protocol.LoginRequestPacket;
import starter.protocol.MessageRequestPacket;
import starter.protocol.RpcRequest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Slf4j
public class DelayStarterTest {
    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient("127.0.0.1", 9999);
        //启动client服务
        client.start();

        Channel channel = client.getChannel();
        //消息体
//        RpcRequest request = new RpcRequest();
//        request.setId(UUID.randomUUID().toString());
//        request.setData("client.message");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername("jackdking");
        packet.setPassword("jackdking@1234");


        //channel对象可保存在map中，供其它地方发送消息
        channel.writeAndFlush(packet);
        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();

        while (true){
            TimeUnit.SECONDS.sleep(2);
            messageRequestPacket.setMessage("send message");
            channel = client.getChannel();
            channel.writeAndFlush(messageRequestPacket);
            log.info("send message :{}",messageRequestPacket.getMessage());
        }
    }
}
