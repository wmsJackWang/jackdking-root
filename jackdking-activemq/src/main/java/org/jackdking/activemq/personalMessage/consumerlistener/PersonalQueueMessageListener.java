package org.jackdking.activemq.personalMessage.consumerlistener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.jackdking.activemq.entity.Student;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

@Component("personalQueueMessageListener")
public class PersonalQueueMessageListener implements SessionAwareMessageListener<Message>{

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		// TODO Auto-generated method stub
		if (message instanceof TextMessage){
            String msg = ((TextMessage) message).getText();
    		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd-hhmmss");

            System.out.println("------------------------------------------");
            System.out.println("消费者收到的消息：" + msg + "——" + sdf.format(new Date()));
            System.out.println("------------------------------------------");

    		
            message.acknowledge();

            try {
                if ("test2".equals(msg)) {
                	System.out.println("test2抛出异常");
                    throw new RuntimeException("故意抛出的异常");
                }
                // 确认消息。只要被确认后  就会出队，接受失败没有确认成功，会在原队列里面
                message.acknowledge();

            } catch (Exception e) {
                // 此不可省略 重发信息使用，如果不写此方法，将不会实现重发操作。失败的消息将会一直在队列中，因为没有进行消息确认。
                // 下次还会监听到这条消息。效果将会是：第一次接受一个消息2。第二次接受2个，依次累加
                    session.recover();
                  	System.out.println("test2消息recover");
                      
            }
        }
        //接收实体类
        if (message instanceof ObjectMessage){
            Student s = (Student) ((ObjectMessage) message).getObject();
            System.out.println("我接收到了实体类消息:"+s);
            message.acknowledge();
        }
        //接收map
        if (message instanceof MapMessage){
            MapMessage mm = (MapMessage) message;
            System.out.println("我接收到了map消息:");
            System.out.println(" get textMessage：\t" + mm.getString("mapId"));
            message.acknowledge();
        }

        //接收byte[]
        if (message instanceof BytesMessage) {
            byte[] b = new byte[1024];
            int len = -1;
            BytesMessage bm = (BytesMessage) message;
            while ((len = bm.readBytes(b)) != -1) {
                System.out.println(new String(b, 0, len));
            }
            message.acknowledge();
        }
        // 如果是Stream消息
        if (message instanceof StreamMessage) {
            StreamMessage sm = (StreamMessage) message;
            System.out.println(sm.readString());
            System.out.println(sm.readInt());
            message.acknowledge();
        }
		
	}

	
}
