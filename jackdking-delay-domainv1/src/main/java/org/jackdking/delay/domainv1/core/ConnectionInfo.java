package org.jackdking.delay.domainv1.core;

import io.lettuce.core.ConnectionId;

public class ConnectionInfo {
    protected ConnectionId connectionId;
    protected String clientId;
    protected String clientIp;
    protected String userName;
    protected String password;
    protected boolean failoverReconnect;

    public ConnectionId getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(ConnectionId connectionId) {
        this.connectionId = connectionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFailoverReconnect() {
        return failoverReconnect;
    }

    public void setFailoverReconnect(boolean failoverReconnect) {
        this.failoverReconnect = failoverReconnect;
    }
}
