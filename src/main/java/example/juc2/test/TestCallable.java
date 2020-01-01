package example.juc2.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 1.futureTask.get() 要在需要结果时在执行
 * 2.多个线程启动，由于缓存，只会执行一次
 */
public class TestCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            TimeUnit.SECONDS.sleep(3);
            System.out.println(Thread.currentThread().getName());
            return "hello";
        });
        new Thread(futureTask).start();

        
        System.out.println(Thread.currentThread().getName() + '\t'+" 完成");
        System.out.println(futureTask.get());
        
    }
}
