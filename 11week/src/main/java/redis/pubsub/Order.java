package redis.pubsub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: lwa
 * @Date: 2021/12/5 18:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    public static final String TOPIC= "order:topic";

    private Long orderId;

}
