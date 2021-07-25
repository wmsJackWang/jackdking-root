package org.jackdking.delay.domainv1.infrastructure.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import org.jackdking.delay.domainv1.infrastructure.attribute.Attributes;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
