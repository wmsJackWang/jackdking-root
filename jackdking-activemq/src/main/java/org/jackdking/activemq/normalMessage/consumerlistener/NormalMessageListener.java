package org.jackdking.activemq.normalMessage.consumerlistener;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("NormalMessageListener")
public class NormalMessageListener implements MessageListener {

	private static final Log log = LogFactory.getLog(NormalMessageListener.class);
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
		String msgText = null;
		try {
			msgText = msg.getText();
			message.acknowledge();//因为
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("== receive message :" + msgText); 
	}

}
