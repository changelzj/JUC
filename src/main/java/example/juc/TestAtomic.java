package example.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile不保证变量的原子性
 *
 *  解决：juc 原子变量：保证变量或数组中每个元素都有原子性和可见性，使用volatile保证可见的特性，cas算法保证原子性
 *  cas（compare and swap）算法是硬件对于并发访问的支持，需要计算机底层的支持
 *  cas算法需要内存值V 预估值A 更新值B三个操作数，当且仅当 V == A时，V = B，否则将不进行任何操作，继续进行下一次修改
 *  cas算法没有阻塞，效率高于锁
 *
 *  i++ 底层需要临时变量赋值完成，每次都是一个不可分割的原子操作，多线程并发时，会影响原子性，导致累加操作分割
 *
 */
public class TestAtomic {
    public static void main(String[] args) {

        /*原子变量保证原子性和内存可见性*/
        Runnable runnable = new Runnable() {
            private AtomicInteger num = new AtomicInteger(0);

            private int getNum() {
                return num.getAndIncrement();
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-> " + this.getNum());
            }
        };

        /*普通变量没有原子性*/
        Runnable runnable2 = new Runnable() {
            private volatile int num = 0;

            private int getNum() {
                return num ++;
            }

            @Override
            public void  run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-> " + this.getNum());
            }
        };



        for (int i = 0; i < 10; i++) {
            new Thread(runnable2, "线程:" + i).start();
        }

    }



}

