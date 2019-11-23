package org.jackdking.activemq.personproducter;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

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
	
}
