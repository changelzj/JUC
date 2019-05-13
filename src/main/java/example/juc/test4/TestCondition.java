package example.juc.test4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
    同步锁的方式进行线程通信：Condition
    解决线程等待唤醒和虚假唤醒
 */
public class TestCondition {
    public static void main(String[] args) {
        Reposity reposity = new Reposity();
        Productor productor = new Productor(reposity);
        Consumer consumer = new Consumer(reposity);
        new Thread(productor, "生产者 1 ").start();
        new Thread(consumer, "消费者 1 ").start();
        new Thread(productor, "生产者 2 ").start();
        new Thread(consumer, "消费者 2 ").start();
    }
}


class Reposity {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void getProduct() {
        lock.lock();
        try {
            while (product < 1) {
                try {
                    System.out.println(Thread.currentThread().getName() + "不足");
                    // 等待
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            product --;
            System.out.println(Thread.currentThread().getName() + " - " + product);
            // 唤醒
            condition.signalAll();
        }
        finally {
            lock.unlock();
        }

    }

    public void addProduct() {
        lock.lock();
        try {
            while (product >= 1) {
                try {
                    System.out.println(Thread.currentThread().getName() + "已满");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            product ++;
            System.out.println(Thread.currentThread().getName() + " - " + product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}


class Productor implements Runnable {
    Reposity reposity = new Reposity();

    public Productor(Reposity reposity) {
        this.reposity = reposity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.reposity.addProduct();
        }
    }
}


class Consumer implements Runnable {
    Reposity reposity = new Reposity();

    public Consumer(Reposity reposity) {
        this.reposity = reposity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            this.reposity.getProduct();
        }
    }
}




