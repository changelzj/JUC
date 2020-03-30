package example.juc.test2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class SynchronizedListTest {
    
    
    /**
     * ConcurrentModificationException !
     * 传统Collection得到的list用Iterator迭代有并发修改异常
     */
    public static void main(String[] args) {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        //List<String> list = new CopyOnWriteArrayList<>();
        
        list.add("a");
        list.add("b");
        list.add("c");
        
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                    list.add("A");
                }
            }).start();
        }

    }
}
