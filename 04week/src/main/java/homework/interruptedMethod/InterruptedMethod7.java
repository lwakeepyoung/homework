package homework.interruptedMethod;

import java.util.concurrent.*;

/**
 * @Author: lwa
 * @Date: 2021/10/17 18:27
 */
public class InterruptedMethod7 {
    public static void main(String[] args) throws Exception {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
        //启动任务线程
        FutureTask<Boolean> futureTask =new FutureTask(new MyCallable1(cyclicBarrier));
        Thread thread = new Thread(futureTask);
        thread.setName("child Thread");
        thread.start();
        cyclicBarrier.reset();
        if(futureTask.get()){
            System.out.println(Thread.currentThread().getName()+"获取返回值，退出主线程");
        }
        System.out.println("启动另一个线程，由另一个线程终止主线程，主线程抢占到时间片会执行一部分代码 ");
    }
}

class MyCallable1 implements Callable{

    private CyclicBarrier cyclicBarrier;

    public MyCallable1(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }

    public Boolean call() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            this.cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        return true;
    }
}
