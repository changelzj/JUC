package example.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 发生垃圾回收后，直接回收
 */
public class Weak {
    public static void main(String[] args) {
        ReferenceQueue<M> queue = new ReferenceQueue<>();
        WeakReference<M> reference = new WeakReference<>(new M() ,queue);

        System.out.println(reference.get());
        System.out.println(queue.poll());
        
        System.gc();
        System.out.println("-----------");
        
        System.out.println(reference.get());
        System.out.println(queue.poll());
    }
}
