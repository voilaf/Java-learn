import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.CountDownLatch;

public class Sub {

    private final static String topName = "test-top";

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        RTopic topic = redisson.getTopic(topName);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int listenerId = topic.addListener(TopicMessage.class, ((channel, msg) -> {
            System.out.println("channel:" + channel.toString());
            System.out.println("msg:" + msg.getFirstName() + " | " + msg.getLastName());
            if ("first".equals(msg.getFirstName())) {
                countDownLatch.countDown();
            }
        }));

        countDownLatch.await();
        topic.removeListener(listenerId);
        redisson.shutdown();
        System.out.println("over");
    }
}
