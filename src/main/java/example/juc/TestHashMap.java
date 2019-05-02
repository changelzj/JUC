package example.juc;

import java.util.concurrent.ConcurrentHashMap;

/**
 *  jdk5后，juc包提供很多并发容器类改善性能
 *
 *  ConcurrentHashMap 线程安全的哈希表，利用锁分段机制保证线程安全，默认16个段
 *  每个线程访问其中一个段，提供了符合操作的方法
 *
 *  但jdk1.8后，ConcurrentHashMap底层变为CAS算法
 *
 *  HashMap 不安全，效率高
 *  HashTable效率很低，锁整个表，复合操作时容易导致线程安全问题（例如：若不存在则添加）
 */
public class TestHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    }
}




