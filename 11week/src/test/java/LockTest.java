import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.Application;
import redis.lock.JedisLockImpl;
import redis.lock.RedisLockImpl;
import redis.lock.RedissonLockImpl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lwa
 * @Date: 2021/12/4 19:07
 */
@SpringBootTest(classes = Application.class)
@Slf4j
public class LockTest {

    @Autowired
    private RedisLockImpl redisLock;

    @Autowired
    private JedisLockImpl jedisLock;

    @Autowired
    private RedissonLockImpl redissonLock;

    @org.junit.jupiter.api.Test
    public void test1(){
        jedisLock.lock(1);
        jedisLock.unLock(1);
    }

    @org.junit.jupiter.api.Test
    public void testRedisTemplate() throws InterruptedException {
        int clientCount = 1_00;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < clientCount; i++) {
            executorService.execute(()->{
                int num = 0;
                Random random = new Random(10);
                while (true){
                    try {
                        redisLock.lock(Thread.currentThread().getId());
                        count.getAndIncrement();
                        //模拟业务处理时间
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        redisLock.unLock(Thread.currentThread().getId());
                        //随机执行n次后结束
                        if(num++==random.nextInt()){
                            countDownLatch.countDown();
                        }
                    }
                }
            });
        }
        countDownLatch.await();
        log.info("end 执行次数:"+count);
    }

    @org.junit.jupiter.api.Test
    public void testJedis() throws InterruptedException {
        int clientCount = 1_00;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < clientCount; i++) {
            executorService.execute(()->{
                int num = 0;
                Random random = new Random(10);
                int end = random.nextInt();
                while (true){
                    try {
                        jedisLock.lock(Thread.currentThread().getId());
                        count.getAndIncrement();
                        //模拟业务处理时间
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        jedisLock.unLock(Thread.currentThread().getId());
                        //随机执行n次后结束
                        if(num++==end){
                            countDownLatch.countDown();
                        }
                    }
                }
            });
        }
        countDownLatch.await();
        log.info("end 执行次数:"+count);
    }

    @org.junit.jupiter.api.Test
    public void testRedisson() throws InterruptedException {
        int clientCount = 1_00;
        CountDownLatch countDownLatch = new CountDownLatch(clientCount);
        ExecutorService executorService = Executors.newFixedThreadPool(clientCount);
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < clientCount; i++) {
            executorService.execute(()->{
                int num = 0;
                Random random = new Random(10);
                int end = random.nextInt();

                while (true){
                    try {
                        redissonLock.lock();
                        count.getAndIncrement();
                        //模拟业务处理时间
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        redissonLock.unLock();
                        //随机执行n次后结束
                        if(num++==end){
                            countDownLatch.countDown();
                        }
                    }
                }
            });
        }
        countDownLatch.await();
        log.info("end 执行次数:"+count);
    }
}
