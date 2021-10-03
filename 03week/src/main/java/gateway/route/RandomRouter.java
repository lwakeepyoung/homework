package gateway.route;

import java.util.List;
import java.util.Random;

/**
 * @Author: lwa
 * @Date: 2021/10/2 10:44
 */
public class RandomRouter implements HttpEndpointRouter{

    @Override
    public String router(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
