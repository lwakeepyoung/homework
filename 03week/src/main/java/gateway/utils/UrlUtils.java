package gateway.utils;

/**
 * @Author: lwa
 * @Date: 2021/10/2 11:15
 */
public class UrlUtils {

    public static String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
}
