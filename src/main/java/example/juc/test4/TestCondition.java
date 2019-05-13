package example.juc.test4;

/*
    同步锁的方式解决线程等待唤醒和虚假唤醒
 */
public class TestCondition {
    public static void main(String[] args) {

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