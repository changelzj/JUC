package example.juc2.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 */
public class TestReenterLock {
    
    public static class Phone {
        
        private Lock lock = new ReentrantLock(false);
        
        public synchronized void sendSms() {
            System.out.println(Thread.currentThread().getName() + " sendsms");
            takePhone();
        }
        
        public synchronized void takePhone() {
            System.out.println(Thread.currentThread().getName() + " takePhone");
        }

        public void sendSms2() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " sendsms2");
                takePhone2();
            } finally {
                lock.unlock();
            }
            
        }

        public void takePhone2() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " takePhone2");
            } finally {
                lock.unlock();
            }
            
        }
    }
    
    
    
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }).start();
        new Thread(() -> {
            phone.sendSms();
        }).start();
        
        new Thread(() -> {
            phone.sendSms2();
        }).start();
        new Thread(() -> {
            phone.sendSms2();
        }).start();
    }
}
