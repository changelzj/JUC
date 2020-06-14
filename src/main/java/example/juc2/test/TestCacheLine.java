package example.juc2.test;

import lombok.SneakyThrows;

/**
 * 测试缓存行对齐
 */
public class TestCacheLine {

    private static T[] arr = new T[2];
    private static Q[] arr2 = new Q[2];
    
    static {
        arr[0] = new T();
        arr[1] = new T();

        arr2[0] = new Q();
        arr2[1] = new Q();
    }
    
    public static void main(String[] args) throws Exception {
        //test1();
        test2();
    }

    /**
     * 同一缓存行，相互通知
     */
    public static void test1() throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000_0000; i++) {
                arr[0].x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000_0000; i++) {
                arr[1].x = i;
            }
        });
        
        long l = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - l)/1000000);
    }



    /**
     * 不同缓存行，不相互通知
    */
    public static void test2() throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000_0000; i++) {
                arr2[0].x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000_0000; i++) {
                arr2[1].x = i;
            }
        });

        long l = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - l)/1000000);
    }
    
    private static class T {
        public volatile long x = 0;
    }
    
    private static class Q extends T {
        public volatile long a1, a2 ,a3 ,a4, a5, a6, a7;
    }
}

