package example.juc.test1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * jdk5后，juc包提供很多并发容器类改善性能
 *
 * ConcurrentHashMap：线程安全的哈希表
 *
 * ConcurrentSkipListMap：tree map的替代
 *
 * CopyOnWriteArrayList：arraylist的替代,适用：读取和遍历多，更新少，线程安全的哈希表，底层利用锁分段机制保证线程安全，
 * 默认16个段，每个段都是独立的锁，并提供了符合操作的方法，jdk1.8后，ConcurrentHashMap底层变为CAS算法
 */
public class TestCollection {

}


class A {
    /**
     * ConcurrentModificationException !
     * 传统Collections.synchronizedList得到的list有并发修改异常
     */
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            List<String> list = Collections.synchronizedList(new ArrayList<>());
            {
                list.add("a");
                list.add("b");
                list.add("c");
            }

            @Override
            public void run() {
                Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                    list.add("A");
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

    }
}




class B {
    /**
     * CopyOnWriteArrayList
     * 添加并复制，添加多时效率低，因为每次添加都会复制，开销很大
     * 并发迭代多时可以提高效率
     */
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            List<String> list = new CopyOnWriteArrayList<>();
            {
                list.add("a");
                list.add("b");
                list.add("c");
            }
            @Override
            public void run() {
                Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    System.out.println(Thread.currentThread().getName() + " : "+iterator.next());
                    list.add("A");
                }
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(runnable, "线程"+i).start();
        }

    }
}


