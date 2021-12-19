package tech.qijin.util4j.lang.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class CountingEvent extends ApplicationEvent {
    public CountingEvent(Object source, Long userId, String countingCode, Long target) {
        super(source);
        this.userId = userId;
        this.countingCode = countingCode;
        this.target = target;
    }

    private Long userId;
    private String countingCode;
    private Long target;
}
