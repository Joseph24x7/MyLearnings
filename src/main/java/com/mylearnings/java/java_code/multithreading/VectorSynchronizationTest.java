package com.mylearnings.java.java_code.multithreading;

import java.util.Vector;

public class VectorSynchronizationTest {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();

        // Create multiple threads to modify the vector concurrently
        Thread writer1 = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                vector.add(i);
            }
        });

        Thread writer2 = new Thread(() -> {
            for (int i = 1001; i <= 2000; i++) {
                vector.add(i);
            }
        });

        // Start the threads
        writer1.start();
        writer2.start();

        try {
            // Wait for the threads to finish
            writer1.join();
            writer2.join();
        } catch (InterruptedException ignored) {
        }

        // Verify the size of the vector
        System.out.println("Vector size: " + vector.size());
    }
}
