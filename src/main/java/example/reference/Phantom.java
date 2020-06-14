package example.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用：永远也get不到，用于管理堆外内存
 */
public class Phantom {
    public static void main(String[] args) {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> reference = new PhantomReference<>(new M(), queue);
        
        System.out.println(reference.get());
        System.out.println(queue.poll());

        System.gc();
        System.out.println("------------");
        
        System.out.println(reference.get());
        System.out.println(queue.poll());
    }
}
