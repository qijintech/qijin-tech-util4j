package tech.qijin.util4j.concurrent.startinggun;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import tech.qijin.util4j.concurrent.NamedThreadFactory;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 发令枪
 * <p>顾名思义，就像运动员起跑用的枪。只有当所有任务都准备好后，才同时执行。通常可用于模拟、测试并发问题</p>
 *
 * @author michealyang
 * @date 2019/2/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class StartingGun {
    private final String THREAD_NAME_PREFIX = "StartingGun";
    private List<Athlete> athletes = Lists.newArrayList();
    private ExecutorService executorService = Executors.newCachedThreadPool(new NamedThreadFactory(THREAD_NAME_PREFIX));
    private CyclicBarrier barrier;
    private StartingGunMode mode;
    private AtomicInteger count = new AtomicInteger(0);
    private int capacity;

    public StartingGun() {
        mode = StartingGunMode.DYNAMIC;
    }

    public StartingGun(int capacity) {
        mode = StartingGunMode.FIXED;
        this.capacity = capacity;
        this.barrier = new CyclicBarrier(capacity);
    }

    public StartingGun addAthlete(Athlete athlete) {
        switch (mode) {
            case FIXED:
                if (count.incrementAndGet() > this.capacity) {
                    return this;
                }
                go(athlete);
                break;
            case DYNAMIC:
                if (athlete != null) {
                    athletes.add(athlete);
                }
        }
        return this;
    }

    public void run() {
        if (StartingGunMode.FIXED.equals(mode)) {
            return;
        }
        if (CollectionUtils.isEmpty(athletes)) {
            log.error("no athletes");
            return;
        }
        barrier = new CyclicBarrier(athletes.size());
        athletes.stream().forEach(athlete -> go(athlete));
    }

    private void go(Athlete athlete) {
        executorService.execute(() -> {
            try {
                // randomSleep(); // just for test
                log.info("athlete {} is waiting", athlete);
                barrier.await();
            } catch (InterruptedException e) {
                log.error("athlete {} is interrupted", athlete, e);
                return;
            } catch (BrokenBarrierException e) {
                log.error("athlete {} encountered broken barrier", athlete, e);
                return;
            }
            log.info("athlete {} is ready", athlete);
            athlete.go();
        });
    }

    /**
     * 发令枪的模式。
     * 当模式为StartingGunMode.FIXED时，需要调用public StartingGun(int capacity)构造函数
     * 当模式为StartingGunMode.DYNAMIC时，需要调用无参构造函数
     * 为StartingGunMode.FIXED模式时，只需调用StartingGun.addAthlete(Athlete athlete)方法即可
     * 为StartingGunMode.DYNAMIC模式时，除了需要调用StartingGun.addAthlete(Athlete athlete)方法，还需调用StartingGun.run()方法
     */
    enum StartingGunMode {
        FIXED,
        DYNAMIC;
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        StartingGun startingGun = new StartingGun();
        startingGun.addAthlete(() -> {
            log.info("athlete1 is running");
        });
        startingGun.addAthlete(() -> {
            log.info("athlete2 is running");
        });
        startingGun.addAthlete(() -> {
            log.info("athlete3 is running");
        });
        startingGun.run();
    }

    public static void test2() {
        StartingGun startingGun = new StartingGun(3);
        randomSleep();
        startingGun.addAthlete(() -> log.info("athlete1 is running"));
        randomSleep();
        startingGun.addAthlete(() -> log.info("athlete2 is running"));
        randomSleep();
        startingGun.addAthlete(() -> log.info("athlete3 is running"));
    }

    public static void randomSleep() {
        long sleep = RandomUtils.nextLong(1000, 3000);
        try {
            Thread.sleep(sleep);
            log.info("thread {} has slept for {} ms", Thread.currentThread().getName(), sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
