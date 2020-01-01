package example.juc2.test;

import java.util.concurrent.TimeUnit;

/**
 * 试题：线程8锁
 * 
 * 1. 同一个资源类，两个同步方法
 * 2. 同一个资源类，两个同步方法,其中一个暂停4秒
 * 3. 同一个资源类，一个普通方法和一个同步方法
 * 4. 同一个资源类，两个静态的同步方法
 * 5. 不同资源类，两个同步方法
 * 6. 不同资源类， 两个静态的同步方法
 * 7. 同一个资源类，一个静态同步方法，一个普通同步方法
 * 8. 不同资源类，一个静态同步方法，一个普通同步方法
 * 
 * synchronized锁住的是当前的资源类(this)而不是当前的同步方法，同一时间只能有同一个线程，
 * 进入资源类访问其中的synchronized方法
 */
public class TestLock8 {
    
    public static class Phone {
        
        public synchronized void sendEmail() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(Exception e) {
                e.printStackTrace();
            }
            System.out.println("sendEmail");
        }
        
        
        public synchronized void sendSMS() {
            System.out.println("sendSMS");
        }
        
        public void send() {
            System.out.println("send");
        }

        public static synchronized void sendEmail2() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(Exception e) {
                e.printStackTrace();
            }
            System.out.println("sendEmail");
        }


        public static synchronized void sendSMS2() {
            System.out.println("sendSMS");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        
        new Thread(() -> {
            phone.sendEmail2();
        }, "A").start();

        TimeUnit.MICROSECONDS.sleep(1);
        
        new Thread(() -> {
            //phone.sendSMS();
            //phone.send();
            //phone2.sendSMS();
            phone.sendSMS2();
        }, "B").start();
        
    }
}
