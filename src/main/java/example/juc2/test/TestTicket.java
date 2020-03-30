package example.juc2.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程卖票案例
 * 多线程共同操纵共享数据
 */
public class TestTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> { for (int i=1; i<1100; i++)  ticket.saleTicket(); }  , "T1").start();
        new Thread(() -> { for (int i=1; i<1100; i++)  ticket.saleTicket(); }  , "T2").start();
        new Thread(() -> { for (int i=1; i<1100; i++)  ticket.saleTicket(); }  , "T3").start();
        
    }
    
    
    
    public static class Ticket {
        private Integer number = 1000;
        private Lock lock = new ReentrantLock(false);//非公平锁
        
        public synchronized void saleTicket() {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number --)+"张，还剩"+number+"张");
            }
        }
        
        public void saleTicketWithLock() {
            lock.lock();
            try {
                if (number > 0) {
                    System.out.println(Thread.currentThread().getName() + "卖出第" + (number --)+"张，还剩"+number+"张");
                }
            } finally {
                lock.unlock();
            }
            
        }
    }
}

