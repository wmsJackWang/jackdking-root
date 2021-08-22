package EventBusTest;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;


/**
 * 事件监听者[Listeners]
 * 只需在指定的方法上加上@Subscribe注解即可
 */
@Slf4j
public class EventBusChangeRecorder {
    @Subscribe
    public void recordCustomerChange(Event e) {
        log.info("接收到消息: " + e.msg);
    }

    @Subscribe
    public void listenInteger(Integer event) {
        log.info("event Integer:" + event);
    }

    @Subscribe
    public void listenLong(Long event) {
        log.info("event Long:" + event);
    }
}
