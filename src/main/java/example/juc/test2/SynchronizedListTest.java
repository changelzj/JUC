package example.juc.test2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class SynchronizedListTest {
    /**
     * ConcurrentModificationException !
     * 传统Collection得到的list用Iterator迭代有并发修改异常
     */
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            List<String> list = new Vector<>();
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
