package example.juc;

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
 *
 */
public class TestVolatile {
    public static void main(String[] args) {
        MyRunable runable = new MyRunable();
        new Thread(runable).start();
        /*while(true) 调用底层代码，效率极高，不会从主存中再次获取被其他线程修改过的数据*/
        while (true) {
            //synchronized (runable) {
                if (runable.isFlag()) {
                    System.out.println("flag is true 主线程结束循环！");
                    break;
                }
            //}

        }

    }
}




class MyRunable implements Runnable {

    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;
        System.out.println("子线程修改flag的值为：  " + isFlag());
    }
}


