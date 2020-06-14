package example.reference;

import java.lang.ref.SoftReference;

/**
 * 内存不充足就回收，否则不回收
 */
public class Soft {
    public static void main(String[] args) {
        SoftReference<Byte[]> reference = new SoftReference<>(new Byte[1024*1024*10]);
        System.out.println(reference.get());
        
        try {
            byte[] b = new byte[30 * 1024 * 1024];
        } finally {
            System.out.println(reference.get());
        }
    }
}
