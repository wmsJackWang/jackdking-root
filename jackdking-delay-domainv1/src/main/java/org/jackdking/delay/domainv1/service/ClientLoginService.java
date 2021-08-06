package org.jackdking.delay.domainv1.service;

import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public interface ClientLoginService {


    //mq服务端的用户名和密码保存在这
    Map<String,String> userInfos = Lists.newArrayList(new String[]{"jack:123456","test:123456"}).stream()
                                    .filter(e-> {return !StringUtils.isEmpty(e);})
                                    .collect(Collectors.toMap((e)->{return e.split(":")[0];},(e)->{return e.split(":")[1];},(k1,k2)->k2));
    public Map.Entry<String , String> getUserInfo(String username);
    public Boolean login(String username , String password);
}
