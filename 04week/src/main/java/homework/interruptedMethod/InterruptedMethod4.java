package homework.interruptedMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: lwa
 * @Date: 2021/10/10 16:07
 */
public class InterruptedMethod4 {
    public static void main(String[] args) throws Exception {
        FutureTask<Boolean> futureTask =new FutureTask(new Callable<Boolean>() {
            public Boolean call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        Thread thread = new Thread(futureTask);
        thread.setName("child Thread");
        thread.start();
        if(futureTask.get()){
            System.out.println(Thread.currentThread().getName()+"获取返回值，退出主线程");
            Thread.currentThread().interrupt();
        }
        System.out.println("主线程的代码执行完毕后优雅的退出主线程");
    }
}
