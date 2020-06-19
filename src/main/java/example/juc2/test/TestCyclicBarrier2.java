package example.juc2.test;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 假设三个人才能共同骑一辆车（凑够三个线程，一起执行）
 */
public class TestCyclicBarrier2 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("凑够三人，出发！");
        });

        for (int i = 0; i < 12; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                @SneakyThrows
                public void run() {
                    System.out.println("第"+ finalI +"位离开校门，前往自行车驿站");
                    TimeUnit.MILLISECONDS.sleep((long)(Math.random()*10000));//模拟每个人速度不一样
                    System.out.println("第"+ finalI +"位到达驿站");
                    cyclicBarrier.await();
                    System.out.println("第"+ finalI +"位开始骑车");
                }
            }).start();
        }
    }
}
