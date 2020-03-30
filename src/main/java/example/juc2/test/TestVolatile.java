package example.juc2.test;

import java.util.concurrent.TimeUnit;

public class TestVolatile {
    public static class Person {
        volatile int age = 20;
    }


    public static void main(String[] args) {
        Person person = new Person();
        
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                person.age = 19;
                System.out.println(Thread.currentThread().getName() +" "+  person.age);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }).start();
        
        while (true) {
            if (person.age == 19) {
                System.out.println(person.age);
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + " exit");
    }
    
    
    
}


