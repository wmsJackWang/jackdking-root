package org.jackdking.delay.domainv1.protocol.request;

import lombok.Data;
import org.jackdking.delay.domainv1.protocol.Packet;
import static org.jackdking.delay.domainv1.protocol.command.Command.TOPIC_MANAGE_REQUEST;

/*
 * 消息主题的创建，删除，修改等
 * 主题跟用户绑定，存储在本地文件
 * 依据 领域建模的原则，server侧可分为 topic存储域、业务身份域。
 * 消费者业务流程：
 *          1. 客户端配置用户名+密码以及topic，启动连接服务端，建立长连接
 *          2. client定期发送心跳消息到server端。
 *          3. server端维护client连接，超过60s心跳消息都没有，则断开client。
 *          4. client发送消息给server端，如果回复消息是连接已断开，则重新走第一步连接。
 *          5. client发送读取消息的命令，server端返回消息集合。
 *          6. client处理完全部消息后，返回ack确认命令。
 * 生产者业务流程：
 *
 */

@Data
public class TopicManageRequestPacket  extends Packet {

    private String topicName;

    private String operation;

    @Override
    public Byte getCommand() {
        return TOPIC_MANAGE_REQUEST;
    }
}
