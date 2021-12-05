package redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: lwa
 * @Date: 2021/12/4 20:32
 */
@Component
@Slf4j
public class RedissonLockImpl {
    @Autowired
    private RedissonClient redissonClient;

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
     * 加锁
     */
    public void lock(){
        RLock lock = redissonClient.getLock(RESOURCE_NAME);
        log.info("获取锁成功");
        lock.lock(LOCK_TIME_OUT, TimeUnit.MILLISECONDS);
    }

    /**
     * 解锁
     */
    public void unLock(){
        RLock lock = redissonClient.getLock(RESOURCE_NAME);
        lock.unlock();
        log.info("释放锁成功");
    }
}
