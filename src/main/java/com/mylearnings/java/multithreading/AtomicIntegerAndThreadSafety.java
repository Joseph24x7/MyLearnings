package com.mylearnings.java.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerAndThreadSafety {
    public static void main(String[] args) {

        // AtomicInteger ensures that the increment operation is thread-safe, and the final counter value is predictable and correct.
        AtomicInteger counter = new AtomicInteger(0);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.incrementAndGet(); // Thread-safe operation
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.incrementAndGet(); // Thread-safe operation
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ignored) {
            System.out.println("Thread interrupted");
        }

        System.out.println("Final Counter value: " + counter.get()); // Thread-safe retrieval
    }
}