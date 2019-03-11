package tech.qijin.util4j.concurrent.parallel;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import tech.qijin.util4j.utils.LogFormat;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/2/19
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class StreamParallel {
    private List<StreamTask> streamActions = Lists.newArrayList();
    //防止并行性能太差，添加同步支持
    private boolean sync = false;

    public StreamParallel add(StreamTask streamAction) {
        streamActions.add(streamAction);
        return this;
    }

    public StreamParallel sync() {
        this.sync = true;
        return this;
    }

    public StreamParallel parallel() {
        this.sync = false;
        return this;
    }

    public void run() {
        if (CollectionUtils.isEmpty(streamActions)) {
            log.warn(LogFormat.builder().message("no task exist").build());
            return;
        }
        long start = System.nanoTime();
        if (sync) {
            streamActions.stream().forEach(StreamTask::doAction);
        } else {
            streamActions.stream().parallel().forEach(StreamTask::doAction);
        }
        long cost = System.nanoTime() - start;
        log.info(LogFormat.builder().message("StreamParallel time cost").put("cost", cost + " ns").build());
    }
}
