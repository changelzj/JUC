package example.reference;

public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + " 被回收");
    }
}
