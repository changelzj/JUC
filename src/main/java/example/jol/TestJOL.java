package example.jol;

import org.openjdk.jol.info.ClassLayout;

public class TestJOL {
    public static void main(String[] args) {
        Object obj = new Object();

        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }
}
