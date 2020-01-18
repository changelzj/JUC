package example.juc2.test;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    
    public static void main(String[] args) throws Exception {
        Task task = new Task(0,207);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = pool.submit(task);
        System.out.println(submit.get());
        pool.shutdown();
    }
    
    public static class Task extends RecursiveTask<Integer> {
        
        private int begin;
        private int end;
        private int result;

        public Task(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        private static final int ADJUST = 10;
        
        @Override
        protected Integer compute() {
            if (end - begin < ADJUST) {
                for (int i = begin; i <= end; i++) {
                    result += i;
                }
            } else {
                int middle = (begin + end)/2;
                Task task01 = new Task(begin, middle);
                Task task02 = new Task(middle+1, end);
                task01.fork();
                task02.fork();
                result = task01.join() + task02.join();
            }
            
            return result;
        }
    } 
}
