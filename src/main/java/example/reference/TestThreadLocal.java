package example.reference;

public class TestThreadLocal {

    private static ThreadLocal<M> threadLocal1 = new ThreadLocal<>();
    
    public static void main(String[] args) {
        threadLocal1.set(new M());
        System.out.println(threadLocal1.get());
    
    }
}
