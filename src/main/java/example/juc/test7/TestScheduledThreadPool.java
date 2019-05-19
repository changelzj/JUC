package example.juc.test7;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 线程池内的线程调度
 */
public class TestScheduledThreadPool {


    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

        ScheduledFuture<Integer> future = pool.schedule(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                int rand = new Random().nextInt(100);
                System.out.println(Thread.currentThread().getName() + " " + rand);
                return rand;
            }

        }, 3, TimeUnit.SECONDS);


        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pool.shutdown();

    }

}



