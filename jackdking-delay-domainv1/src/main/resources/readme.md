#现状

1. 基于redis的zset数据结构实现 简单的延迟队列

参考：https://www.cnblogs.com/throwable/p/11955154.html 和 https://www.cnblogs.com/throwable/p/11955159.html

#目标

1. 独立部署延迟域服务。
	将延迟队列能力独立集成在一个独立的服务中，对外输出能力，提供能力的方式目前以api接口的方式接入延迟服务。服务成功保存消息后，同步返回:"SUCCESS"。
2. 对外输出延迟服务的 延迟能力。
          本延迟服务的服务delayApi：延迟服务域名：服务端口/delay/sendDelayMsg。消息参数以参数的形式传递到延迟服务上。
          具体url：localhost:8080/delay/sendDelayMsg?expireTime=1010&uniqueKey=AAAAA
          
          	private long expireTime;//未来过期的时间点 ， 作为zset数据结构的score值，redis是使用这个score值来对zset中的元素排序的。
			
			private String uniqueKey;//消息的唯一key，作为zset数据结构的member值。
    
3. 使用lua脚本重构服务和redis服务器的交互，保证延迟消息不丢失。

	1)消息发送的可靠性
		 业务线服务使用延迟服务，使用delayApi发送一笔延迟消息，同步返回SUCCESS后,服务就能保证消息保存在服务上。
		 问题：如果不将消息保存在数据库上，而是直接发往redis，那么可能存在，服务返回FAIL，但是redis可能已经保存了该消息。因此需要业务线控制消息的检查(返回失败的延迟消息推送到业务线侧的时候做好检查工作)
		 解决方案：因为redis网络延迟或者其他非业务校验不通过而失败的消息，我们都会做好持久化，异步任务会检查这些消息并保证消息不被消费，而且会立马去延迟队列检查并删除消息。
		 
		使用lua脚本发送延迟消息，lua脚本在redis中所作的工作是：将消息主体数据放入到zset排队队列中，并将消息详情数据放入到对应的hash数据结构当中。
		 
	2)消息消费的可靠性
		消息的消费使用lua脚本，参考：https://www.cnblogs.com/throwable/p/11955154.html 和 https://www.cnblogs.com/throwable/p/11955159.html
   
	
4. 实现延迟消息触发的方式：第一种基于服务调用方式;第二种是基于消息队列，订阅延迟能力的服务需要监听分配给他的消息队列。
5. 延迟服务集群部署+负载均衡，保证服务的可靠性。

#topic型消息流程设计


 消息主题的创建，删除，修改等
 主题跟用户绑定，存储在本地文件
 依据 领域建模的原则，server侧可分为 topic存储域、业务身份域。
 消费者业务流程：
          1. 客户端配置用户名+密码以及topic，启动连接服务端，建立长连接
          2. client定期发送心跳消息到server端，server端handler更新client的channel最近活跃时间
          3. server端维护client连接，超过60s心跳消息都没有，则断开client。（已实现）
          4. client发送消息给server端，如果回复消息是连接已断开，则重新走第一步连接，client端不断重试。
          5. client发送读取消息的命令，server端返回消息集合。
          6. client处理完全部消息后，返回ack确认命令。
 生产者业务流程：
          1. 生产者端，根据配置的topic名+用户名+密码，启动客户端并建立长连接
          2. client定期发送心跳消息到server端，server端handler更新client的channel最近活跃时间
          3. 发送消息到mq，同步发送，等待server端结果。
          4. 批量发送消息，同步发送，等待server端结果。

#Protobuf通信协议的使用
当前即时通讯应用中最热门的通信协议无疑就是Google的Protobuf了，基于它的优秀表现，微信和手机QQ这样的主流IM应用也早已在使用它。
本开源延迟消息中间件的c-s通信也是使用这个通信协议。


#Netty的使用

1.SimpleChannelInboundHandler 组件的使用
	1) 这个组件是能够自动识别数据包类型的handler组件，这个能力能避免开发者开发请求包类型判断。

        try {
            if (this.acceptInboundMessage(msg)) {
                this.channelRead0(ctx, msg);
            } else {
                release = false;
                ctx.fireChannelRead(msg);
            }
        } finally {
            if (this.autoRelease && release) {
                ReferenceCountUtil.release(msg);
            }

        }

2.IdleStateHandler 组件的使用
	1)管道数据数据传输检测，如果规定时间内没有数据的读写，则服务端自动释放关闭channel。
	2)这个handler需要结合心跳发送handler使用（HeartBeatTimerHandler）,当然也可以在IdleStateHandler子类中实现。