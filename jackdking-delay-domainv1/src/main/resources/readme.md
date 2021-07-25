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
