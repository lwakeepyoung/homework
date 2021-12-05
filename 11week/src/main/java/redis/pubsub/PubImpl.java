package redis.pubsub;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: lwa
 * @Date: 2021/12/5 18:51
 */
@Component
public class PubImpl {
    @Autowired
    private RedissonClient redissonClient;

    public void producer(Order order){
        RTopic topic = redissonClient.getTopic(Order.TOPIC,new SerializationCodec());
        long publish = topic.publish(order);
        System.out.println(publish);
    }
}
