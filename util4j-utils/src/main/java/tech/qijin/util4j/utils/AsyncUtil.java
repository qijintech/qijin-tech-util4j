package tech.qijin.util4j.utils;

import tech.qijin.util4j.lang.function.JustRun;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author michealyang
 * @date 2019-12-19
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
public class AsyncUtil {

    private static final int THREAD_POOL_FACTOR = 4;
    private static final int MAX_CAPACITY = 10000;
    private static final String THREAD_PREFIX = "ASYNC";

    private static ExecutorService service = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 4,
            Runtime.getRuntime().availableProcessors() * THREAD_POOL_FACTOR,
            0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(MAX_CAPACITY),
            new NamedThreadFactory(THREAD_PREFIX));

    public static void submit(Supplier supplier) {
        service.submit(() -> supplier.get());
    }

    public static void submit(JustRun justRun) {
        service.submit(() -> justRun.run());
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String namePrefix;


        public NamedThreadFactory(String prefix) {

            StringBuffer sb = new StringBuffer();
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = sb.append(prefix).append("-THREAD-").toString();
        }

        public Thread newThread(Runnable r) {

            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
