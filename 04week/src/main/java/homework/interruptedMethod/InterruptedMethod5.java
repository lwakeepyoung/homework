package homework.interruptedMethod;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author: lwa
 * @Date: 2021/10/10 16:07
 */
public class InterruptedMethod5 {
    public static void main(String[] args) throws Exception {
        //启动退出线程
        ExitThread exitThread = new ExitThread("退出线程");
        exitThread.start();
        //启动任务线程
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
        thread.setDaemon(true);
        thread.start();
        if(futureTask.get()){
            System.out.println(Thread.currentThread().getName()+"获取返回值，退出主线程");
            exitThread.setExit(true);
        }

        System.out.println("启动另一个线程，由另一个线程终止主线程，主线程抢占到时间片会执行一部分代码");

        int count = 0;
        while (true){
            System.out.println(++count);
        }
    }
}

class ExitThread extends Thread{

    private Boolean isExit = false;

    public ExitThread(String name){
        this.setName(name);
    }

    @Override
    public void run() {
        while (true){
            if(isExit){
                System.exit(0);
            }
        }
    }

    public void setExit(Boolean exit) {
        isExit = exit;
    }
}
