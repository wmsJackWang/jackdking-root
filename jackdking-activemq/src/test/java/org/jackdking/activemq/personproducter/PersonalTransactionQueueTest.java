package org.jackdking.activemq.personproducter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalTransactionQueueTest {

	@Resource(name="personalTransactionJmsTemplate")
	private JmsTemplate personalTransactionJmsTemplate;
	 
	
	
	@Test
	public void test() {
		 
		int i = 0;
		while(true) {
			try {
				TimeUnit.MILLISECONDS.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++i;
			send(i);
		}
	}
	
	private void send(final int i) {
		// TODO Auto-generated method stub
		personalTransactionJmsTemplate.send(new MessageCreator() {
		
		@Override
		public Message createMessage(Session session) throws JMSException {
			// TODO Auto-generated method stub
		TextMessage  msg = null;
		if(i%3!=0)
			msg = session.createTextMessage("personalTransactionMessage-"+i);
		else
			msg = session.createTextMessage("test2");
					
				
				return msg;
			}
		});
	} 
	 
	
	public static final String broker_url = "failover:(tcp://localhost:61616)";
	private static String queue_name = "personal_queue";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, broker_url);
		// 通过工厂创建一个连接
		Connection connection = factory.createConnection();
		// 启动连接
		connection.start();
		// 创建一个session会话 事务 自动ack
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		// 创建一个消息队列
		Destination destination = session.createQueue(queue_name);
		// 创建生产者
		MessageProducer producer = session.createProducer(destination);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd-hhmmss");
		// 消息持久化
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//		TextMessage message = session.createTextMessage("test delay message:" + sdf.format(new Date()));
		TextMessage message = session.createTextMessage("test2");
		
		long time = 6 * 1000;// 延时1min
//		long period = 10 * 1000;// 每个10s
		int repeat = 6;// 6次
		message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
//		message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
//		message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
		// 发送消息
		producer.send(message);
		session.commit();//开启事务，如果没有提交，消息队列里是没有消息的。
		producer.close();
		session.close();
		connection.close();
	}

	
	
}
