package redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lwa
 * @Date: 2021/12/4 18:00
 */
@Component
@Slf4j
public class RedisLockImpl {

    @Autowired
    private RedisTemplate redisTemplate;

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
     * 简单加锁
     * @param lockerId 持有者id
     * @return
     */
    public boolean lock(Object lockerId) {
        Assert.notNull(lockerId,"参数错误");
        int tryCount = 0;
        while (tryCount<TRY_GET_LOCK_MAX_COUNT){
            // nx px
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(RESOURCE_NAME, lockerId, LOCK_TIME_OUT, TimeUnit.MILLISECONDS);
            if(!locked){
                try {
                    tryCount++;
                    log.info("locker:"+lockerId+"获取锁失败，尝试重新获取锁......");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                log.info("locker:"+lockerId+"获取锁成功");
                return locked;
            }
        }
        return false;
    }

    /**
     * 简单解锁
     * @param lockerId
     * @return
     */
    public boolean unLock(Object lockerId) {
        //使用lua脚本解锁保证原子性
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("unlock.lua"));
        redisScript.setResultType(Long.class);
        Object execute = redisTemplate.execute(redisScript, Stream.of(RESOURCE_NAME).collect(Collectors.toList()), lockerId);
        Boolean result = execute.equals(1L);
        if(result){
            log.info("locker:"+lockerId+"释放锁成功");
        }else {
            log.info("locker:"+lockerId+"释放锁失败");
        }
        return result;
        //Available since 6.2.0. 是否等同于使用脚本? 猜测可以释放锁，但是无法区别释放了谁的锁。仅可以操作字符串
        //redisTemplate.opsForValue().getAndDelete(RESOURCE_NAME);
    }

}
