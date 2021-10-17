package homework.interruptedMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

/**
 * @Author: lwa
 * @Date: 2021/10/10 16:07
 */
public class InterruptedMethod6 {


    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        //启动任务线程
        FutureTask<Boolean> futureTask =new FutureTask(new MyCallable(countDownLatch));
        Thread thread = new Thread(futureTask);
        thread.setName("child Thread");
        thread.start();
        countDownLatch.await();
        if(futureTask.get()){
            System.out.println(Thread.currentThread().getName()+"获取返回值，退出主线程");
        }
        System.out.println("启动另一个线程，由另一个线程终止主线程，主线程抢占到时间片会执行一部分代码");
    }
}

class MyCallable implements Callable{

    private CountDownLatch countDownLatch;

    public MyCallable(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    public Boolean call() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.countDownLatch.countDown();
        return true;
    }
}


