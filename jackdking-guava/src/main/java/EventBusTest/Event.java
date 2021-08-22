package EventBusTest;


import lombok.Builder;
import lombok.Getter;

/**
 * 自定义事件
 */
@Builder
@Getter
public class Event {
    String msg;

    public Event(String msg) {
        this.msg = msg;
    }
}
