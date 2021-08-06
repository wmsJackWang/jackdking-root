package org.jackdking.delay.domainv1.service.impl;

import org.jackdking.delay.domainv1.service.ClientLoginService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientLoginServiceImpl implements ClientLoginService {
    @Override
    public Map.Entry<String, String> getUserInfo(String username) {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        return userInfos.entrySet().stream()
                .filter(e-> !Objects.isNull(e)&&username.equals(e.getKey()))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public Boolean login(String username, String password) {

        Map.Entry<String,String> userinfo = getUserInfo(username);
        if(userinfo==null) {
            return false;
        }
        Assert.isTrue(!StringUtils.isEmpty(password),"password cannt be empty");

        if(!userinfo.getValue().equals(password)) {
            return false;
        }
        return true;
    }
}
