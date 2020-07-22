package example.juc2.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    
    private static Lock lock = new ReentrantLock();
    private static int i = 0;

    /**
     * 我曾七次鄙视自己的灵魂: 
     * 第一次,当它本可进取时，却故作谦卑； 
     * 第二次,当它空虚时，用爱欲来填充； 
     * 第三次,在困难和容易之间，它选择了容易； 
     * 第四次,它犯了错，却借由别人也会犯错来宽慰自己； 
     * 第五次,它自由软弱，却把它认为是生命的坚韧； 
     * 第六次,当它鄙夷一张丑恶的嘴脸时，却不知那正是自己面具中的一副； 
     * 第七次,它侧身于生活的污泥中虽不甘心，却又畏首畏尾。
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                increment();
            }).start();
        }
    }

    public static void increment() {
        if (lock.tryLock()) {
            try {
                i++;
                System.out.println(i);
                //TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    } 
        
    
}
