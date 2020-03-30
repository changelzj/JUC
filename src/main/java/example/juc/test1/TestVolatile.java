package example.juc.test1;

/**
 * 内存可见性
 *
 * 内存可见性：多个线程访问共享数据时彼此不可见，JVM会对每一个线程提供独立的缓存，用于提高效率，修改共享数据时，
 * 从堆内存（主存）取数据到线程的缓存中，修改后在放回主存
 *
 * 解决内存可见性的方法：
 *  1.synchronized同步锁：可以每次刷新线程的缓存，但效率很低下
 *  2.volatile：保证多线程操作共享数据时，内存中数据相互可见，底层采用内存栅栏实时将缓存数据刷新，效率低于不用volatile修饰，
 *  但效率高于锁，性能降低的原因：底层不能再使用JVM底层的重排序优化
 *
 * volatile只能保证内存可见性，1不具备锁的互斥性，2不能保证变量的原子性
 * 原子性：完整性，不可分割，某个线程做某个业务时，中间不可以被加塞或分割
 *
 */
public class TestVolatile {
    
    static volatile boolean flag = false;
    
    public static void main(String[] args) {
        
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            flag = true;
            
            System.out.println("子线程修改flag的值为：  " + flag);
            
        }).start();
        
        
        // while(true) 调用底层代码，效率极高，不会从主存中再次获取被其他线程修改过的数据
        while (true) {
            if (flag) {
                System.out.println("flag is true 主线程结束循环！");
                break;
            }

        }

    }
}







