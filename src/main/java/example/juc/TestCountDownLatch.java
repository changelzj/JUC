package example.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：在完成某些运算时，只有其他所有运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        // 闭锁，初始的大小与线程数量一致
        CountDownLatch latch = new CountDownLatch(10);
        MyRun run = new MyRun(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(run).start();
        }

        try {
            // 其他线程没有执行完成，主线程等待
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("共用时：" + (end - start));

    }
}

class MyRun implements Runnable {

    private CountDownLatch latch;

    public MyRun(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10000; i++) {
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() +" - "+ i);
                }
            }
        }
        finally {
            // 执行完一次，闭锁-1，为保证闭锁一定-1，使用finally 语句块
            this.latch.countDown();
        }


    }
}


