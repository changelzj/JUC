package example.juc;

/**
 * 生产者消费者案例：虚假唤醒
 *
 * 生产者线程：添加创建数据的线程
 * 消费者线程：删除和销毁数据的线程
 *
 * 为了避免虚假唤醒，wait应该总是使用在循环中
 */
public class TestProductorAndConsumer {
    public static void main(String[] args) {
        Reposity reposity = new Reposity();
        Productor productor = new Productor(reposity);
        Consumer consumer = new Consumer(reposity);
        new Thread(productor, "生产者 1 ").start();
        new Thread(consumer, "消费者 1 ").start();
        new Thread(productor, "生产者 2 ").start();
        new Thread(consumer, "消费者 2 ").start();
    }
}

class Reposity {
    private int product = 0;

    public synchronized void getProduct() {
        while (product < 1) {
            try {
                System.out.println(Thread.currentThread().getName() + "不足");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product --;
        System.out.println(Thread.currentThread().getName() + " - " + product);
        this.notifyAll();



    }

    public synchronized void addProduct() {
        while (product >= 1) {
            try {
                System.out.println(Thread.currentThread().getName() + "已满");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product ++;
        System.out.println(Thread.currentThread().getName() + " - " + product);
        this.notifyAll();




    }
}


class Productor implements Runnable {
    Reposity reposity = new Reposity();

    public Productor(Reposity reposity) {
        this.reposity = reposity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.reposity.addProduct();
        }
    }
}


class Consumer implements Runnable {
    Reposity reposity = new Reposity();

    public Consumer(Reposity reposity) {
        this.reposity = reposity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            this.reposity.getProduct();
        }
    }
}


