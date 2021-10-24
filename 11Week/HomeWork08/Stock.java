import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.ByteArrayCodec;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.util.Arrays;

public class Stock {

    private final RedissonClient redisClient = RedisFactory.create();

    private String name;

    Stock(String name) {
        this.name = name;
    }

    public boolean decrease(int step) {
        RScript script = redisClient.getScript();
        String luaScript = "local counter = redis.call('get', KEYS[1]);\n" +
                "if (counter >= ARGV[1]) then \n" +
                "redis.call('decrby', KEYS[1], ARGV[1])\n" +
                "return 1;\n" +
                "end;\n" +
                "return 0";
        long result = script.eval(RScript.Mode.READ_WRITE,
                luaScript,
                RScript.ReturnType.INTEGER,
                Arrays.asList(name),
                step
        );
        return result > 0;
    }

    public void shutdown() {
        redisClient.shutdown();
    }

    private static class RedisFactory {

        private RedisFactory() {}

        public static RedissonClient create() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            Codec codec = new JsonJacksonCodec();
            config.setCodec(codec);
            return Redisson.create(config);
        }
    }
}
