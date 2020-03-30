package example.juc2.test;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决CAS算法的ABA问题
 */
public class TestAtomicStampedReference {
    

    public static void main(String[] args) {

        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);
        
        // ABA操作
        new Thread(() -> {
            System.out.println(stampedReference.getStamp());
            
            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp()+1);
            System.out.println(stampedReference.getStamp());
            
            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp()+1);
            System.out.println(stampedReference.getStamp());
            
        }).start();
        
        new Thread(() -> {
            try {

                int stamp = stampedReference.getStamp();
                System.out.println(stamp);
                TimeUnit.SECONDS.sleep(3);
                System.out.println(stampedReference.compareAndSet(100, 95, stamp, stamp+1));

                System.out.println(stampedReference.getStamp());
                
                System.out.println(stampedReference.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }).start();
    }
    
}
