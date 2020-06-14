package example.juc2.test;

import lombok.SneakyThrows;

/**
 * 小程序：证明乱序执行的存在
 */
public class TestDisorder {
    
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;
    
    @SneakyThrows
    public static void main(String[] args) {
        
        int i = 0;
        
        while (true) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });
            
            t1.start();
            t2.start();
            t1.join();
            t2.join();

            if (x == 0 && y == 0) {
                System.out.println(i);
                System.out.println(x);
                System.out.println(y);
                return;
            }
        }
    }
}
 