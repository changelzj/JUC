package example.juc.test7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 工作窃取模式
 * 将一个大任务拆分成多个小任务，直到不能再拆分，然后将结果合并
 *
 */
public class TestForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumCalculate calculate = new SumCalculate(0L, 100_0000_0000L);
        Long invoke = pool.invoke(calculate);
        System.out.println(invoke);
    }
}



/**
 * RecursiveTask 可以返回值
 * RecursiveAction 不能返回值
 */
class SumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    public static final long THURSHOLD = 10000000000L;

    public SumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;

            SumCalculate leftSumCalculate = new SumCalculate(start, middle);
            leftSumCalculate.fork();

            SumCalculate rightSumCalculate = new SumCalculate(middle+1, end);
            rightSumCalculate.fork();

            return leftSumCalculate.join() + rightSumCalculate.join();
        }

    }
}





