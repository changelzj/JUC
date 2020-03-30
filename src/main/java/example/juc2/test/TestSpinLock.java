package example.juc2.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁的实现
 * 原子引用线程
 */
public class TestSpinLock {
    
    public static class SpinThread {
        
        private AtomicReference<Thread> reference = new AtomicReference<>();
        
        public void lock() {
            System.out.println(Thread.currentThread().getName() + " lock");
            Thread thread = Thread.currentThread();
            do {
                
            } while ( !reference.compareAndSet(null, thread) );
        }

        public void unlock() {
            Thread thread = Thread.currentThread();
            reference.compareAndSet(thread, null);
            System.out.println(Thread.currentThread().getName() + " unlock");
        }
    }
    
    
    
    
    public static void main(String[] args) {
        SpinThread atomicThread = new SpinThread();
        
        new Thread(() -> {
            
            atomicThread.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicThread.unlock();
            
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicThread.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicThread.unlock();
        }).start();

    }
}
