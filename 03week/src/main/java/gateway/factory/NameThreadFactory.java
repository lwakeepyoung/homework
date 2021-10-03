package gateway.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: lwa
 * @Date: 2021/10/2 11:40
 */
public class NameThreadFactory implements ThreadFactory {

    private final ThreadGroup group;

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    private final boolean daemon;

    public NameThreadFactory(String namePrefix,boolean daemon){
        this.daemon = daemon;
        SecurityManager securityManager = System.getSecurityManager();
        group = securityManager!=null?
                securityManager.getThreadGroup():
                Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    public NameThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        return t;
    }
}
