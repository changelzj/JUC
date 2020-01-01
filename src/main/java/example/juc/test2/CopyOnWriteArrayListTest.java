package example.juc.test2;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    /**
     * CopyOnWriteArrayList
     * 添加并复制，添加多时效率低，因为每次添加都会复制，开销很大
     * 并发迭代多时可以提高效率
     * 
     * CopyOnWriteArrayList：arraylist的替代,适用：读取和遍历多，更新少，线程安全的哈希表，
     * 底层利用锁分段机制保证线程安全，默认16个段，每个段都是独立的锁，并提供了符合操作的方法，
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
