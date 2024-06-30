package jackdking.dt.message.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import jackdking.dt.common.toolkit.JSONUtil;
import jackdking.dt.message.enums.NotifyTypeEnum;
import jackdking.dt.message.request.AlarmNotifyRequest;
import jackdking.dt.message.request.ChangeParameterNotifyRequest;
import jackdking.dt.message.service.JackDKingDtpSendMessageService;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

@Slf4j
public class JackDKingDtpSendMessageServiceImpl implements JackDKingDtpSendMessageService, CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("JackDKingDtpSendMessageServiceImpl init");
        }
    }

    @Override
    public void sendAlarmMessage(NotifyTypeEnum typeEnum, AlarmNotifyRequest alarmNotifyRequest) {
        if (log.isDebugEnabled()) {
            log.debug("JackDKingDtpSendMessageServiceImpl notifyTypeEnum:{}, alarmNotifyRequest:{}", typeEnum.name(), JSONUtil.toJSONString(alarmNotifyRequest));
        }
    }

    @Override
    public void sendChangeMessage(ChangeParameterNotifyRequest changeParameterNotifyRequest) {
        if (log.isDebugEnabled()) {
            log.debug("JackDKingDtpSendMessageServiceImpl changeParameterNotifyRequest:{}", JSONUtil.toJSONString(changeParameterNotifyRequest));
        }
    }
}
