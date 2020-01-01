package example.juc2.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThreadOrderAccess {
    
    public static class ShareResource {
        
        private String flag = "A";
        
        private Lock lock = new ReentrantLock();
        
        private Condition conditionA = lock.newCondition();
        private Condition conditionB = lock.newCondition();
        private Condition conditionC = lock.newCondition();
        
        public void doA() {
            lock.lock();
            try {
                while (flag != "A") {
                    conditionA.await();
                }

                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" +i);
                }
                
                flag = "B";
                conditionB.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
               lock.unlock(); 
            }
        }

        public void doB() {
            lock.lock();
            try {
                while (flag != "B") {
                    conditionB.await();
                }

                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" +i);
                }

                flag = "C";
                conditionC.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void doC() {
            lock.lock();
            try {
                while (flag != "C") {
                    conditionC.await();
                }

                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" +i);
                }

                flag = "A";
                conditionA.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        
    }

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.doA();
            }
        }, "A").start();
        
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.doB();
            }
        }, "B").start();
        
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.doC();
            }
        }, "C").start();
    }
}
