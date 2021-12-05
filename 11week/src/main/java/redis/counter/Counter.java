package redis.counter;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lwa
 * @Date: 2021/12/4 22:30
 */
@Component
@Slf4j
public class Counter {

    @Autowired
    private RedissonClient redissonClient;

    public static final String RESOURCE_NAME = "repository:count";

    public static final String REPOSITORY_COUNT_REDIS_KEY = "goods:count";

    /**
     * 自动释放锁的时间
     */
    public static final int LOCK_TIME_OUT = 30_000;
    /**
     * 模拟库存
     */
    @PostConstruct
    public void initCount(){
        RBucket<Integer> bucket = redissonClient.getBucket(REPOSITORY_COUNT_REDIS_KEY);
        bucket.set(100_0000);
    }

    /**
     * 查询当前库存
     * @return
     */
    public Integer getCurrCount(){
        RBucket<Integer> bucket = redissonClient.getBucket(REPOSITORY_COUNT_REDIS_KEY);
        return bucket.get();
    }

    /**
     * 减少库存数量
     * @param reduceCount
     * @return
     */
    public Boolean reduce(int reduceCount){
        RLock lock = redissonClient.getLock(RESOURCE_NAME);
        Boolean flag = true;
        try {
            lock.lock(LOCK_TIME_OUT, TimeUnit.MILLISECONDS);
            RBucket<Integer> bucket = redissonClient.getBucket(REPOSITORY_COUNT_REDIS_KEY);
            // 当前库存
            Integer currCount = bucket.get();
            int newCount = currCount-reduceCount;
            if(newCount>0){
                log.info("减少库存成功:"+reduceCount+".剩余库存:"+newCount);
                bucket.set(newCount);
            }else {
                log.info("减少库存失败:"+reduceCount+".剩余库存:"+currCount);
                flag = false;
            }
        }finally {
            lock.unlock();
        }
        return flag;
    }
}
