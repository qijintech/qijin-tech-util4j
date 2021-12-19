package tech.qijin.util4j.lang.event;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public abstract class EventBase {
    protected Date date;
    protected Long userId;
    protected Long delta;

    public abstract EventKind getKind();
}
