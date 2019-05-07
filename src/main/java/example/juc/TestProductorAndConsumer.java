package example.juc;

/**
 * 生产者消费者案例
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
        new Thread(productor, "productor01").start();
        new Thread(consumer, "consumer01").start();
        new Thread(productor, "productor02").start();
        new Thread(consumer, "consumer02").start();
    }
}

class Reposity {
    private int product = 0;

    public synchronized void getProduct() {
        if (product <= 0) {
            System.out.println(Thread.currentThread().getName() + " 缺少库存");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            product --;
            System.out.println(Thread.currentThread().getName() + " - " + product);
            this.notifyAll();
        }
    }

    public synchronized void addProduct() {
        if (product >= 10) {
            System.out.println(Thread.currentThread().getName() +" 库存满了");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            product ++;
            System.out.println(Thread.currentThread().getName() + " - " + product);
            this.notifyAll();
        }

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


