package example.juc2.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class TestReadWriteLock {
    
    public static class MyCache {
        private volatile Map<String, String> map = new HashMap<>();
        private ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public void write(String key, String valle) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "准备写入数据");
                TimeUnit.SECONDS.sleep(2);
                map.put(key, valle);
                System.out.println(Thread.currentThread().getName() + "写入数据完成");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        public void read(String key) {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "准备读取数据");
                TimeUnit.SECONDS.sleep(2);
                String s = map.get(key);
                System.out.println(Thread.currentThread().getName() + " 读取数据 " + s);
            } catch (Exception e) {
                
            } finally {
                lock.readLock().unlock();
            }
        }
    }
    
    
    
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.write(finalI +"", finalI+"");
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.read(finalI +"");
            }).start();
        }
    }
}
