package tech.qijin.util4j.lang.event;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class FeedPublishEvent extends EventBase{
    @Override
    public EventKind getKind() {
        return EventKind.FEED_PUBLISH;
    }
}
