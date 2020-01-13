package example.juc.test7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池
 * 底层维护线程队列，保存所有等待状态的线程，避免频繁创建销毁线程的额外开销，提高响应速度
 *
 * ---- java.util.concurrent.Executor 负责线程使用调度的根接口
 *   -- ExecutorService 子接口，线程池的主要接口
 *      -- ThreadPoolExecutor 线程池的实现类
 *      -- ScheduledExecutorService 子接口， 用于线程调度
 *          -- ScheduledThreadPoolExecutor 实现类：既能做线程池，又能调度
 */
public class TestThreadPool {
    public static void main(String[] args) {

        //线程池的常见工具类
        // 缓存线程池，线程池数量不固定，可以根据需求自动更改数量
        Executors.newCachedThreadPool();
        // 单个线程池，池中只有一个线程
        Executors.newSingleThreadExecutor();

        // 创建固定大小的线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);
        // 创建固定大小，可以延时或定时执行任务的线程池
        Executors.newScheduledThreadPool(10);

        // 分配任务Runnable
        pool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });

        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                int sum = 0;
                for (int i = 0; i < 100; i++) {
                    sum += i;
                }
                return sum;
            }
        });
        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 等待所有任务完成在关闭，而且不接受新任务
        pool.shutdown();

        // 立即关闭所有线程
        // pool.shutdownNow(); 
    }
}



