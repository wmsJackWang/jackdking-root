package org.jackdking.delay.domainv1.core;

import io.netty.channel.Channel;
import org.jackdking.delay.domainv1.netty.JdkMQConnectionFactory;

import javax.jms.*;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.jms.TopicSession;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdkMQConnection implements Connection, TopicConnection, QueueConnection {

    private final IdGenerator clientIdGenerator;
    private ConnectionInfo connectionInfo;
    private String connectionId;
    private Channel channel;
    private final CopyOnWriteArrayList<JdkMQSession> sessions = new CopyOnWriteArrayList<>();
    private final LongSequenceGenerator sessionIdGenerator = new LongSequenceGenerator();

    public JdkMQConnection(IdGenerator clientIdGenerator) {
        this.clientIdGenerator = clientIdGenerator;
    }

    public JdkMQConnection(Channel channel, IdGenerator clientIdGenerator) {

        this.clientIdGenerator = clientIdGenerator;
        this.channel = channel;
    }

    protected void setUserName(String userName) {
        this.connectionInfo.setUserName(userName);
    }

    protected void setPassword(String password) {
        this.connectionInfo.setPassword(password);
    }

    @Override
    public QueueSession createQueueSession(boolean b, int i) throws JMSException {
        return null;
    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Queue queue, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }

    @Override
    public TopicSession createTopicSession(boolean b, int i) throws JMSException {
        return null;
    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Topic topic, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }

    @Override
    public Session createSession(boolean b, int i) throws JMSException {
        return null;
    }

    @Override
    public Session createSession(int i) throws JMSException {
        return null;
    }

    @Override
    public Session createSession() throws JMSException {
        return null;
    }

    @Override
    public String getClientID() throws JMSException {
        return null;
    }

    @Override
    public void setClientID(String s) throws JMSException {

    }

    @Override
    public ConnectionMetaData getMetaData() throws JMSException {
        return null;
    }

    @Override
    public ExceptionListener getExceptionListener() throws JMSException {
        return null;
    }

    @Override
    public void setExceptionListener(ExceptionListener exceptionListener) throws JMSException {

    }

    @Override
    public void start() throws JMSException {

    }

    @Override
    public void stop() throws JMSException {

    }

    @Override
    public void close() throws JMSException {

    }

    @Override
    public ConnectionConsumer createConnectionConsumer(Destination destination, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }

    @Override
    public ConnectionConsumer createSharedConnectionConsumer(Topic topic, String s, String s1, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }

    @Override
    public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String s, String s1, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }

    @Override
    public ConnectionConsumer createSharedDurableConnectionConsumer(Topic topic, String s, String s1, ServerSessionPool serverSessionPool, int i) throws JMSException {
        return null;
    }
}
