package example.juc;

/**
 * CAS算法
 */
public class TestCompareAndSwap {

    private static final CompareAndSwap cas = new CompareAndSwap();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread("线程" + i) {
                @Override
                public void run() {
                    int expecteVal = cas.getValue();
                    boolean b = cas.compareAndSet(expecteVal, (int) (Math.random()*100));
                    System.out.println(b);
                }

            }.start();
        }
    }

}



class CompareAndSwap {

    private volatile int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized int compareAndSwap(int expecteVal, int newVal) {
        int oldVal = value;
        if (expecteVal == oldVal) {
            this.value = newVal;
        }
        return oldVal;
    }

    public synchronized boolean compareAndSet(int expecteVal, int newVal) {
        return expecteVal == this.compareAndSwap(expecteVal, newVal);
    }



}


