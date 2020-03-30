package example.juc2.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池本质上是一个 ThreadPoolExecutor
 * 
 * public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler)
 */
public class TestThreadPool {
    public static void main(String[] args) {
        
        //fixed();
        //single();
        //cached();
        initPool();
        //cached2();
    }

    /**
     * 固定大小的线程池
     */
    public static void fixed() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行");
                });

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * 只有一个线程
     */
    public static void single() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行");
                });

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * 自动扩容
     */
    public static void cached() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行");
                });

                //TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
    
    

    public static void cached2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                Future<String> future = executorService.submit(() -> {
                    return Thread.currentThread().getName() + " 执行";
                });
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
    
    /**
     * 
     */
    public static void initPool() {

        ExecutorService executorService = new ThreadPoolExecutor(
                2, // 核心线程数
                Runtime.getRuntime().availableProcessors() + 2, // CPU密集型程序，取核数加1 | 2
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
         );

        try {
            for (int i = 0; i < 10000; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 执行");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //executorService.shutdown();
        }
    } 
}



