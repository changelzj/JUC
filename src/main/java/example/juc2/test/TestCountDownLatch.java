package example.juc2.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 让一批线程全部完成后，在继续执行
 */
public class TestCountDownLatch {
    
    public static void main(String[] args) throws InterruptedException {
        
        CountDownLatch countDownLatch = new CountDownLatch(6);
        
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }, "Thread"+i).start();
        }

        // 阻塞于此处，直到计数器降为零
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName());
    }
}
