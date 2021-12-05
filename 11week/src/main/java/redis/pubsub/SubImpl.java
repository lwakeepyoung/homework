package redis.pubsub;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: lwa
 * @Date: 2021/12/5 18:51
 */
@Component
public class SubImpl {

    @Autowired
    private RedissonClient redissonClient;


    public void consumer(){
        RTopic rTopic = redissonClient.getTopic(Order.TOPIC,new SerializationCodec());
        rTopic.addListener(Order.class,
                (channel, order) -> System.out.println("线程id:"+Thread.currentThread().getId()+"处理订单:"+order.getOrderId()));
    }
}
