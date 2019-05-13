package example.juc.test2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步锁：与同步块，同步方法一样用于处理线程安全，是一种显式的锁
 */
public class TestLock {
    public static void main(String[] args) {
        TicketTask ticketTask = new TicketTask();
        for (int i = 0; i < 10; i++) {
            new Thread(ticketTask, "线程" + i).start();
        }
    }
}


class TicketTask implements Runnable {

    private int number = 500;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {
            lock.lock();
            try {
                if (number > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    number--;
                    System.out.println(Thread.currentThread().getName() + "完成售票：" + number);
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }

        }

    }
}

