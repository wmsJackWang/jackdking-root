package org.jackdking.delay.domainv1.infrastructure.attribute;

import io.netty.util.AttributeKey;
import org.jackdking.delay.domainv1.infrastructure.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
