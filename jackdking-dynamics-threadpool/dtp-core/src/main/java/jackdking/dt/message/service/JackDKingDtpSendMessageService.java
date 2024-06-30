package jackdking.dt.message.service;

import jackdking.dt.message.enums.NotifyTypeEnum;
import jackdking.dt.message.request.AlarmNotifyRequest;
import jackdking.dt.message.request.ChangeParameterNotifyRequest;

public interface JackDKingDtpSendMessageService {

    /**
     * Send dynamic thread pool alert notifications.
     *
     * @param typeEnum
     * @param alarmNotifyRequest
     */
    void sendAlarmMessage(NotifyTypeEnum typeEnum, AlarmNotifyRequest alarmNotifyRequest);

    /**
     * Send dynamic thread pool parameter change notification.
     *
     * @param changeParameterNotifyRequest
     */
    void sendChangeMessage(ChangeParameterNotifyRequest changeParameterNotifyRequest);
}
