package example.juc.test6;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：效果：读写互斥，写写互斥，读读之间不互斥
 * 可以多线程并发持有读锁
 * 写锁是线程独占的
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWrite readWrite = new ReadWrite();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWrite.read();
                }
            },"read ").start();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                readWrite.write((int) (Math.random()*101) );
            }
        },"write ").start();

    }


}

class ReadWrite {

    private int num = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " " + num);
        } finally {
            lock.readLock().unlock();
        }

    }

    public void write(int num) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        } finally {
            lock.writeLock().unlock();
        }

    }
}



