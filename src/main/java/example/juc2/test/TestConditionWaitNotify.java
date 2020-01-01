package example.juc2.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestConditionWaitNotify {
    
    public static class AirConditioner {
        
        private int number = 0;
        
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increment() {
            lock.lock();
            try {
                while (number != 0) {
                    condition.await();
                }
                number ++;
                System.out.println(Thread.currentThread().getName()+" 修改为 "+number);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            
        }

        public void decrement() {
            lock.lock();
            try {
                while (number == 0) {
                    condition.await();
                }
                number --;
                System.out.println(Thread.currentThread().getName()+" 修改为 "+number);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                airConditioner.increment();
            }
        }, "T1").start();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                airConditioner.decrement();
            }
        }, "T2").start();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                airConditioner.increment();
            }
        }, "T3").start();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                airConditioner.decrement();
            }
        }, "T4").start();

    }
}
