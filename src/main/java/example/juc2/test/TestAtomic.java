package example.juc2.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomic {
    public static class Data {
        private volatile int num = 0;
        private AtomicInteger integer = new AtomicInteger();
    }

    public static void main(String[] args) {
        Data data = new Data();
        
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    data.num ++;
                    data.integer.getAndIncrement();
                }
            }).start();
        }
        
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        
        System.out.println(data.num);
        System.out.println(data.integer.get());
    }
}
