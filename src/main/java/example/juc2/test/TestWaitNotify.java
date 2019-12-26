package example.juc2.test;

/**
 * 线程的通信和调度
 * 实现
 */
public class TestWaitNotify {
    public static class AirConditioner {
        private int number = 0;
        
        public synchronized void increment() throws InterruptedException {
            while (number != 0) {
                this.wait();
            }
            number ++;
            System.out.println(Thread.currentThread().getName()+" 修改为 "+number);
            this.notifyAll();
        }
        
        public synchronized void decrement() throws InterruptedException {
            while (number == 0) {
                this.wait();
            }
            number --;
            System.out.println(Thread.currentThread().getName()+" 修改为 "+number);
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();
        
        new Thread(() -> {
            for (int i=0;i<10;i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T1").start();
        
        new Thread(() -> {
            for (int i=0;i<10;i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T2").start();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T3").start();

        new Thread(() -> {
            for (int i=0;i<10;i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "T4").start();
        
    }
}



