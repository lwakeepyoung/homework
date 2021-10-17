package conc0301.op01;

/**
 * @Author: lwa
 * @Date: 2021/10/10 15:46
 */
public class Join {
    public static void main(String[] args) {
        Object oo = new Object();

        MyThread thread1 = new MyThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();

        synchronized (oo) {  // 这里用oo或thread1/this
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        oo.wait(0);
                        //thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }
}

class MyThread extends Thread {
    private String name;

    private Object oo;

    public MyThread(String name){
        this.name = name;
    }

    public void setOo(Object oo) {
        this.oo = oo;
    }

    @Override
    public void run() {
        // 这里用oo或this，效果不同
        synchronized (oo) {
            for (int i = 0; i < 100; i++) {
                System.out.println(name + i);
            }
        }
    }
}
