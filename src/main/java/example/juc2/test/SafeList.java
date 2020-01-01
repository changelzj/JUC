package example.juc2.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * List不安全
 */
public class SafeList {
    /**
     * ArrayList不安全，不加锁， 效率高 ConcurrentModificationException
     * Vector 线程安全，加锁, 效率低
     * 
     * Collections.synchronizedList(new ArrayList<>()) 将线程不安全转换为线程安全
     * 
     * CopyOnWriteArrayList 线程安全的JUC集合工具类，读写分离，写时复制，实现并发读而不需要加锁
     * 
     * 
     * 
     */
    public static void main(String[] args) {
        // testArrayList();
        // testVector();
        // testCopyOnWriteArrayList();
        testSynchronizedList();
    }
    
    
    public static void testArrayList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
    
    
    public static void testSynchronizedList() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
    
    public static void testVector() {
        List<String> list = new Vector<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
    
    public static void testCopyOnWriteArrayList() {
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
