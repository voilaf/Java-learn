import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Pub {

    private final static String topName = "test-top";

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        RTopic topic = redisson.getTopic(topName);

        topic.publish(new TopicMessage("first", "last"));

        redisson.shutdown();
    }
}
