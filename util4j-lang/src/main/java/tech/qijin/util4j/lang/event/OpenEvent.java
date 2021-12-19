package tech.qijin.util4j.lang.event;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class OpenEvent extends EventBase {
    @Override
    public EventKind getKind() {
        return EventKind.OPEN;
    }
}
