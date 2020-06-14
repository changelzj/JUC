package example.reference;

import java.util.Map;
import java.util.WeakHashMap;

public class TestWeakMap {
    public static void main(String[] args) {
        Map<Object, Object> map = new WeakHashMap<>();
        Object obj = new Object();
        map.put(obj, "world");
        System.out.println(map);
        obj = null;
        System.gc();
        System.out.println(map);
    }
}
