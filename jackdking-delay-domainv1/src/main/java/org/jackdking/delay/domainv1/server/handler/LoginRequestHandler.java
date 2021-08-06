package org.jackdking.delay.domainv1.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jackdking.delay.domainv1.infrastructure.session.Session;
import org.jackdking.delay.domainv1.infrastructure.util.IDUtil;
import org.jackdking.delay.domainv1.infrastructure.util.SessionUtil;
import org.jackdking.delay.domainv1.protocol.request.LoginRequestPacket;
import org.jackdking.delay.domainv1.protocol.response.LoginResponsePacket;
import org.jackdking.delay.domainv1.service.ClientLoginService;
import org.jackdking.delay.domainv1.service.impl.ClientLoginServiceImpl;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private static ClientLoginService clientLoginService = new ClientLoginServiceImpl();

    protected LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = IDUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), ctx.channel());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        if(Objects.isNull(loginRequestPacket)||StringUtils.isEmpty(loginRequestPacket.getUserName())
                                              ||StringUtils.isEmpty(loginRequestPacket.getPassword())) {
            return false;
        }
        return clientLoginService.login(loginRequestPacket.getUserName(),loginRequestPacket.getPassword());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }
}
