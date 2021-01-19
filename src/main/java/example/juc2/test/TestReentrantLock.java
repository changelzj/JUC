package example.juc2.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    
    private static Lock lock = new ReentrantLock();
    private static int i = 0;

    /**
     * tryLock 和 tryLock(time) 尝试获取锁，获取不到不等待
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                //tryLock();
                try {
                    tryLockTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void tryLock() {
        if (lock.tryLock()) {
            try {
                i++;
                System.out.println(i);
                //TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    public static void tryLockTime() throws InterruptedException {
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                i++;
                System.out.println(i);
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }
}
