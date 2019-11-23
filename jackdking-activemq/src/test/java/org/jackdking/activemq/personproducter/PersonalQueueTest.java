package org.jackdking.activemq.personproducter;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ScheduledMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalQueueTest {

	@Resource(name="personalJmsTemplate")
	private JmsTemplate personalJmsTemplate;
	
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
	personalJmsTemplate.send(new MessageCreator() {
		
		@Override
		public Message createMessage(Session session) throws JMSException {
			// TODO Auto-generated method stub
		TextMessage  msg = null;
		if(i%3!=0)
			msg = session.createTextMessage("test-personalMessage-"+i);
		else
			msg = session.createTextMessage("test2");
		long time = 60 * 1000;
		//延迟60秒:
		msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);	
		
//		//延迟30秒，投递10次，间隔10秒:
//		long delay = 30 * 1000;
//        long period = 10 * 1000;
//        int repeat = 9;
//        msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
//        msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
//        msg.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
        
        //使用 CRON 表达式
//        msg.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0/5 * * * *");
        	
		return msg;
			}
		});
	} 
}
