package example.juc2.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 * Collection -> Queue => BlockingQueue
 * 
 * ArrayBlockingQueue
 * 
 * 
 * 
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test3();
        //test2();
        synchronousQueue();
    }

    /**
     * 异常
     */
    public static void test1() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // add 添加内容进入队列，可重复，如果满了会抛异常
        System.out.println(queue.add("a"));
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        // System.out.println(queue.add("d"));

        // 检查队列有没有元素，没有则抛异常，有则返回首元素
        System.out.println(queue.element());
        
        // 移除队列中元素，先进先出，如果越过边界会抛异常
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        // System.out.println(queue.remove());

        
    }

    public static void test2() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        // offer 添加元素如果越界会返回false
        System.out.println(queue.offer("A"));
        System.out.println(queue.offer("B"));
        System.out.println(queue.offer("C"));
        System.out.println(queue.offer("D", 3, TimeUnit.SECONDS));

        // 移除队列中元素，先进先出，如果越界会返回false
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll(3, TimeUnit.SECONDS));

        //  检查队列有没有元素，没有则返回空，有则返回首元素
        System.out.println(queue.peek());
    }

    /**
     * 阻塞
     */
    public static void test3() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        // 只要队列满了，就会阻塞
        queue.put("A");
        queue.put("B");
        queue.put("C");
        
        System.out.println(queue.take());
        
        TimeUnit.SECONDS.sleep(5);
        queue.put("D");
        
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    /**
     * 没有容量，每一个put必须等待一个take
     */
    public static void synchronousQueue() {
        BlockingQueue<String> queue = new SynchronousQueue<>(true);
        
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " put a");
                queue.put("a");
                
                System.out.println(Thread.currentThread().getName() + " put b");
                queue.put("b");
                
                System.out.println(Thread.currentThread().getName() + " put c");
                queue.put("c");
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() +"\t" + queue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() +"\t" + queue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() +"\t" + queue.take());
            } 
            catch (Exception e) {e.printStackTrace();}
            
        }).start();
    }

    
    
}




