package example.juc.test2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestCallable {
    /**
     * 除了thread runable 外的第三种创建线程的方式
     * 比较runable ，Callable既可以返回值，又能抛出异常，又带有泛型
     */
    public static void main(String[] args) {
        MyCallable callable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        Integer integer = null;
        try {
            // 类似闭锁，执行完成才会得到结果
            integer = futureTask.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(integer);
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Integer sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }

}


