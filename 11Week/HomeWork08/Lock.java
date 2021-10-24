import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lock {

    private final static String lockName = "self-lock";

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);


        ExecutorService executor = Executors.newFixedThreadPool(10);

        int nums = 20;
        CountDownLatch countDownLatch = new CountDownLatch(nums);
        try {
            for (int index=0; index<nums; index++) {
                executor.submit(() -> {
                    RLock lock = redisson.getLock(lockName);
                    try {
                        lock.lock();

                        int random = new Random().nextInt(9999);
                        System.out.println("lock-start:" + random);

                        Thread.sleep(100);

                        System.out.println("lock-end:" + random);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();

                        countDownLatch.countDown();
                    }
                });
            }
        } finally {
            countDownLatch.await();
            redisson.shutdown();
            executor.shutdown();
        }
    }

    public static RLock getLock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        return redisson.getLock(lockName);
    }
}
