package org.jackdking.activemq.normalproducter;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NormalMessageTest 
{
	@Resource(name="notifyJmsTemplate")
	private JmsTemplate jmsTemplate ;
	
	@Resource(name="jmsTemplate")
	private JmsTemplate tradNotifyTemplate;
	
    @Test
	public void test() {
		int i = 0;
    	while(true) {
    		try {
				TimeUnit.MICROSECONDS.sleep(10);
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
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage  msg = session.createTextMessage("notify-normalMessage-"+i);
				return msg;
			}
		});
		
		tradNotifyTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage  msg = session.createTextMessage("trade-normalMessage-"+i);
				return msg;
			}
		});
	}
	
}
