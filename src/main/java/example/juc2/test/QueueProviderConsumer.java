package example.juc2.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueProviderConsumer {

    public static class Data {
        private volatile boolean flag = true;
        private AtomicInteger integer = new AtomicInteger(0);
        private BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        
        public void product() throws Exception {
            while (flag) {
                int i = integer.incrementAndGet();
                if ( queue.offer(i, 2, TimeUnit.SECONDS) ) {
                    System.out.println("插入队列 " + Thread.currentThread().getName()+" " + i);
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
        
        public void consume() throws Exception {
            while (flag) {
                System.out.println("消费移除" + Thread.currentThread().getName() +" "+ queue.poll(2, TimeUnit.SECONDS));
                TimeUnit.SECONDS.sleep(2);
            }
            
        }
    }
    
    
    /**
     * 生产消费
     */
    public static void main(String[] args) throws Exception {
        Data data = new Data();
        
        new Thread(() -> {
            try {
                data.product();
            } 
            catch (Exception e) {e.printStackTrace();}

        }).start();

        new Thread(() -> {
            try {
                data.consume();
            } 
            catch (Exception e) {e.printStackTrace();}
        }).start();
        
        TimeUnit.SECONDS.sleep(7);
        data.flag = false;
    }
}
