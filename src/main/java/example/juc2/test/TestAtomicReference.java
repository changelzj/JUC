package example.juc2.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;

public class TestAtomicReference {
    @Data
    @AllArgsConstructor
    public static class User {
        private Integer age;
        private String name;
    }

    public static void main(String[] args) {
        User user = new User(1, "liming");
        User user1 = new User(2, "yangyang");
        AtomicReference<User> atomicReference = new AtomicReference<>(user);
        System.out.println(atomicReference.compareAndSet(user, user1));
        System.out.println(atomicReference.get());
        System.out.println(atomicReference.compareAndSet(user, user1));
        System.out.println(atomicReference.get());
    }
}
