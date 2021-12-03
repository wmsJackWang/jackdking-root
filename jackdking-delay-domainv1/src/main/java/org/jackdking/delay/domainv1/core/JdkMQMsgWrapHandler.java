package org.jackdking.delay.domainv1.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jackdking.delay.domainv1.core.hook.MessageEventHook;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

import javax.websocket.MessageHandler;

/*
 * 所有consumer都继承这个类
 * 这个handler会拿到channel的消息，并根据消息类型来下发给consumer
 */
@Slf4j
public class JdkMQMsgWrapHandler<T> extends ChannelInboundHandlerAdapter implements JdkMQMsgHandler {

    final public static String proxyMappedName = "handleMsg";
    protected MessageEventHook<T> messageEventHook;
    private JdkMQMsgWrapHandler<T> jdkMQMsgWrapHandler;

    @Override
    public void handleMsg(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);


        ProxyFactory weaver = new ProxyFactory(jdkMQMsgWrapHandler);
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName(JdkMQMsgWrapHandler.proxyMappedName);
        advisor.setAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                log.info("proxyFactory execute before");
                return methodInvocation.proceed();
            }
        });
        weaver.addAdvisor(advisor);

        JdkMQMsgHandler proxyObject = (JdkMQMsgHandler) weaver.getProxy();
        proxyObject.handleMsg(ctx, msg);

    }
}
