package example.juc2.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * 异步回调
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //runAsync();
        supplyAsync();
        
    }

    /**
     * 异步调用，没有返回
     */
    public static void runAsync()  throws ExecutionException, InterruptedException {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " no return");
        });

        voidCompletableFuture.get();
    }

    /**
     * 异步调用，有返回值
     */
    public static void supplyAsync() throws ExecutionException, InterruptedException {
        Integer integer = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "  return");
            return 1 / 0;
        }).whenComplete((result, exception) -> {
            System.out.println(result);
            System.out.println(exception);
        }).exceptionally(e -> {
            System.out.println(e);
            return -100;
        }).get();
        System.out.println(integer);
    }
    
    
}
