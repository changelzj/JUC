package example.juc2.test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 * 用于并发数量控制和多个共享资源的互斥使用
 * 
 * acquire要么线程获得信号量，信号量减1，要么使线程一直等下去，直到有线程释放信号量或超时
 * release会将信号量的值加1，然后唤醒等待的线程
 */
public class TestSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢占");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
                
            }, "线程" + i).start();
        }
    }
}
