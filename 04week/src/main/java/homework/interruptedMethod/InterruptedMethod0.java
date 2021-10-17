package homework.interruptedMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: lwa
 * @Date: 2021/10/10 15:55
 */
public class InterruptedMethod0 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Boolean> futureTask =new FutureTask(new Callable<Boolean>() {
            public Boolean call() {
                return true;
            }
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        if(futureTask.get()){
            System.out.println(futureTask.get());
            System.exit(0);
        }
        System.out.println("主线程的代码未执行完退出主线程");


    }


}
