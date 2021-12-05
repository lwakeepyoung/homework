package redis.config;

import io.lettuce.core.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: lwa
 * @Date: 2021/12/4 17:12
 */
@Configuration
public class RedisConfig {

    @Bean
    public JedisPool initJedisPool(){
        JedisPool jedisPool = new JedisPool();
        return jedisPool;
    }

    @Bean
    public RedissonClient initRedissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
