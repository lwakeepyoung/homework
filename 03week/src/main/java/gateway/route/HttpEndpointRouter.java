package gateway.route;

import java.util.List;

/**
 * @Author: lwa
 * @Date: 2021/10/1 15:46
 */
public interface HttpEndpointRouter {

    String router(List<String> urls);

}
