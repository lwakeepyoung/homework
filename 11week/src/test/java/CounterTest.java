import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.Application;
import redis.counter.Counter;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lwa
 * @Date: 2021/12/4 22:56
 */
@SpringBootTest(classes = Application.class)
@Slf4j
public class CounterTest {

    @Autowired
    private Counter counter;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Test
    public void testCounter() throws InterruptedException {
        Integer initCount = counter.getCurrCount();
        int threadNum = Runtime.getRuntime().availableProcessors() + 1;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(()->{
                Random random = new Random();
                int num = random.nextInt(10);
                for (int j = 0; j < num; j++) {
                    int reduceCount= random.nextInt(10_000);
                    if(counter.reduce(reduceCount)){
                        atomicInteger.getAndAdd(reduceCount);
                    };
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("初始库存为:"+initCount);
        System.out.println("消耗库存为:"+atomicInteger.get());
        Integer currCount = counter.getCurrCount();
        System.out.println("当前库存为:"+currCount);
    }
}
