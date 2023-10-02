package com.mylearnings.java.multithreading;

import lombok.Getter;

public class VolatileExample {
    private static volatile boolean flag = false;

    public void toggleFlag() {
        flag = !flag; // Toggle the flag
    }

    public boolean isFlag() {
        return flag;
    }

    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();

        // Thread 1: Sets the flag to true
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Thread 1: Flag is "+example.isFlag());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(i==60) {
                    example.toggleFlag();
                }
            }
        });

        // Thread 2: Reads the flag
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 2: Flag is "+example.isFlag());
            }
        });

        thread1.start();
        thread2.start();
    }
}
