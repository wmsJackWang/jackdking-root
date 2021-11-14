package org.jackdking.delay.domainv1.netty;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.jackdking.delay.domainv1.client.handler.LoginResponseHandler;
import org.jackdking.delay.domainv1.config.NettyClustersConfig;
import org.jackdking.delay.domainv1.core.*;
import org.jackdking.delay.domainv1.core.hook.ConnectionHook;
import org.jackdking.delay.domainv1.core.hook.ConnectionTryLoginHook;
import org.jackdking.delay.domainv1.infrastructure.codec.PacketDecoder;
import org.jackdking.delay.domainv1.infrastructure.codec.PacketEncoder;
import org.jackdking.delay.domainv1.infrastructure.constants.JdkMQConstant;
import org.springframework.util.Assert;

import javax.jms.*;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @MethodName: 
 * @Param: 
 * @return: 
 * @Author: jackdking
 * @User: 10421
 * @Date: 2021/11/13
 **/ 
public class JdkMQConnectionFactory implements ConnectionFactory, QueueConnectionFactory, TopicConnectionFactory, MakeObject, MapBuilder {

    private long DEFAULT_TIME_OUT = 10 * 1000;//10秒
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9999;

    private ChannelInboundHandlerAdapter channelInboundHandlerAdapter = null;
    private Bootstrap bootstrap = null;
    private long timeout = DEFAULT_TIME_OUT;//Default timeout could be changed, so have to be a single variable.
    private EventLoopGroup eventLoopGroup = null;
    private Channel messageChannel = null;
    private DefaultEventExecutorGroup defaultEventExecutorGroup;
    private JdkMQProperties properties = null;
    private JdkMQConnection jdkMQConnection = null;
    private ConnectionInfo info = null;
    private NettyClustersConfig nettyClustersConfig = new NettyClustersConfig();
    private ConnectionHook connectionConnectSuccessHook = null;
    private ConnectionHook connectionTryLoginHook = null;//两种hook，一种是键盘输入，另一种正规的消费者和生产者登录模块。
    private String host = HOST;
    private int port = PORT;
    private int retry = MAX_RETRY;
    private boolean connected = false;

    private final IdGenerator clientIdGenerator = new IdGenerator("JdkMQClient");
    private String clientIDPrefix;
    private final IdGenerator connectionIdGenerator = new IdGenerator("JdkMQConnection");
    private String connectionIDPrefix;

    ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat(JdkMQConstant.JdkMQConnectionFactory_Format)
            .setDaemon(true)
            .build();

    //生产者和消费者创建时候，传入的处理器,作为netty参数来初始化Bootstrap对象。
    private ChannelInboundHandlerAdapter messageHandler = null;
    private Throwable throwable;

    public JdkMQConnectionFactory() {

    }

    public void init() {

        defaultEventExecutorGroup = new DefaultEventExecutorGroup(NettyClustersConfig.getWorkerThreads(), threadFactory);
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(defaultEventExecutorGroup);
                        channel.pipeline().addLast(new PacketDecoder());
                        channel.pipeline().addLast(new PacketEncoder());
                        channel.pipeline().addLast(messageHandler);
                    }
                })
                .option(ChannelOption.SO_SNDBUF, nettyClustersConfig.getClientSocketSndBufSize())
                .option(ChannelOption.SO_RCVBUF, nettyClustersConfig.getClientSocketRcvBufSize())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false);
    }

    /*
     * this method is ConnectionFactory start to connect JdkMQ
     */
    public void connect() {
        Preconditions.checkNotNull(messageHandler, "messageHandler is null");
        init();

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();

                Optional.ofNullable(connectionConnectSuccessHook)
                        .ifPresent(hook -> {
                            //登入成功，调用成功连接钩子。
                            hook.hookConnectionEvent(channel);
                });

                Optional.ofNullable(connectionTryLoginHook)
                        .ifPresent((hook -> {
                            try {
                                //开始进行身份登入
                                hook.hookConnectionEvent(channel);
                                connected = true;
                                jdkMQConnection = new JdkMQConnection(this, clientIdGenerator);

                            }catch (Throwable throwable) {
                                connected = false;
                                this.throwable = throwable;
                            }
                        }));
                //登入失败，直接打印失败错误
                if (Objects.nonNull(throwable)) {
                    this.close();//释放资源
                    throwable.printStackTrace();
                }

            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
                this.close();//释放资源
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> {
                    --retry;
                    connect();
                }, delay, TimeUnit.SECONDS);
            }
        });

    }

    public JdkMQConnection getJdkMQConnection() {
        return Optional.ofNullable(this.jdkMQConnection)
                .orElseThrow(() -> {
                    return new RuntimeException("jdkMQConnection is null");
                });
    }

    public void setConnectionTryLoginHook(ConnectionHook connectionTryLoginHook) {
        this.connectionTryLoginHook = connectionTryLoginHook;
    }

    public JdkMQConnectionFactory(JdkMQProperties properties) {
        Assert.notNull(properties, "Properties must not be null");
        this.properties = properties;
    }

    public void setConnectionConnectSuccessHook(ConnectionHook connectionConnectSuccessHook) {
        this.connectionConnectSuccessHook = connectionConnectSuccessHook;
    }

    public void setMessageHandler(ChannelInboundHandlerAdapter messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void close() {
        if (messageChannel != null) {
            try {
                messageChannel.close().sync();
                eventLoopGroup.shutdownGracefully();
                defaultEventExecutorGroup.shutdownGracefully();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public QueueConnection createQueueConnection() throws JMSException {
        return null;
    }

    @Override
    public QueueConnection createQueueConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public TopicConnection createTopicConnection() throws JMSException {
        return null;
    }

    @Override
    public TopicConnection createTopicConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection() throws JMSException {
        return null;
    }

    @Override
    public Connection createConnection(String s, String s1) throws JMSException {
        return null;
    }

    @Override
    public JMSContext createContext() {
        return null;
    }

    @Override
    public JMSContext createContext(String s, String s1) {
        return null;
    }

    @Override
    public JMSContext createContext(String s, String s1, int i) {
        return null;
    }

    @Override
    public JMSContext createContext(int i) {
        return null;
    }

    @Override
    public Object makeObject() {
        return null;
    }

    /**
     * @Description: TODO build Object reference instance from properties file
     * @MethodName:
     * @Param:
     * @return:
     * @Author: jackdking
     * @User: 10421
     * @Date: 2021/11/14
     **/
    @Override
    public void buildFromMap(Map<String, Object> map) {


    }

    public static void main(String[] args) {
        testJdkMQConnectionFactory();
    }

    public static void testJdkMQConnectionFactory() {

        JdkMQConnectionFactory jdkMQConnectionFactory = new JdkMQConnectionFactory();
        jdkMQConnectionFactory.setConnectionTryLoginHook(new ConnectionTryLoginHook());
        jdkMQConnectionFactory.setMessageHandler(new LoginResponseHandler());
        jdkMQConnectionFactory.init();
        jdkMQConnectionFactory.connect();

    }

}
