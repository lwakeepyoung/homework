import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.Application;
import redis.pubsub.Order;
import redis.pubsub.PubImpl;
import redis.pubsub.SubImpl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lwa
 * @Date: 2021/12/5 19:08
 */
@SpringBootTest(classes = Application.class)
public class PubSubTest {
    @Autowired
    private SubImpl sub;
    @Autowired
    private PubImpl pub;

    @Test
    public void test() throws InterruptedException {
        //增加监听器，模拟消费订单 可以增加多个订阅者
        sub.consumer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Random random = new Random(100000L);
        for (int i = 0; i < 10; i++) {
            //模拟产生订单
            executorService.execute(()->{
                while (true){
                    pub.producer(new Order(random.nextLong()));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        TimeUnit.SECONDS.sleep(60);
    }
}
