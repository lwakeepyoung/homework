package redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lwa
 * @Date: 2021/12/4 20:32
 */
@Component
@Slf4j
public class JedisLockImpl {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 资源前缀
     */
    private static final String LOCK_PREFIX = "redis:lock:";
    /**
     * 资源名称
     */
    private static final String RESOURCE_NAME = LOCK_PREFIX+"resource";
    /**
     * 自动释放锁的时间
     */
    public static final int LOCK_TIME_OUT = 30_000;
    /**
     *  尝试获取锁的最大次数
     */
    public static final int TRY_GET_LOCK_MAX_COUNT = 10;

    /**
     *  nx px
     */
    SetParams setParams = SetParams.setParams().nx().px(LOCK_TIME_OUT);

    /**
     * 简单加锁
     * @param lockerId
     * @return
     */
    public Boolean lock(Object lockerId){
        Assert.notNull(lockerId,"参数错误");
        Jedis jedis = jedisPool.getResource();
        int tryCount = 0;
        while (tryCount<TRY_GET_LOCK_MAX_COUNT){
            // nx px
            String lock = jedis.set(RESOURCE_NAME,lockerId.toString(),setParams);
            if("OK".equals(lock)){
                log.info("locker:"+lockerId+"获取锁成功");
                return true;
            }
            try {
                tryCount++;
                log.info("locker:"+lockerId+"获取锁失败，尝试重新获取锁......");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jedis.close();
        return false;
    }

    /**
     * 简单解锁
     * @param lockerId
     * @return
     */
    public Boolean unLock(Object lockerId){
        Jedis jedis = jedisPool.getResource();
        try {
            String script = StreamUtils.copyToString(new ClassPathResource("unlock.lua").getInputStream(), StandardCharsets.UTF_8);
            //使用lua脚本解锁保证原子性
            Object eval = jedis.eval(script,
                    Stream.of(RESOURCE_NAME).collect(Collectors.toList()),
                    Stream.of(lockerId.toString()).collect(Collectors.toList()));
            Boolean result = "1".equals(eval.toString());
            if(result){
                log.info("locker:"+lockerId+"释放锁成功");
            }else {
                log.info("locker:"+lockerId+"释放锁失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return false;
    }

}
