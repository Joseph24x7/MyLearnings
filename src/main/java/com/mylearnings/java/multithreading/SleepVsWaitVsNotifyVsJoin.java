package com.mylearnings.java.multithreading;

public class SleepVsWaitVsNotifyVsJoin {
    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedResource = new SharedResource();

        // Producer thread
        Thread producerThread = new Thread(() -> {
            try {
                sharedResource.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                sharedResource.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread.start();
        producerThread.join(); // join is used to make the current thread wait until another thread completes its execution.
        consumerThread.start();


    }
}

class SharedResource {
    private boolean produced = false;

    public synchronized void produce() throws InterruptedException {

        System.out.println("Producer thread is producing...");

        // sleep is a method provided by the Thread class, it makes the current running thread to wait for certain amount of time.
        Thread.sleep(10000); // Simulate some work

        produced = true; // Produce data

        // notify is used to wake up a waiting thread when a certain condition is met or a shared resource is ready.
        this.notify(); // Notify the waiting consumer

        System.out.println("Producer thread released the lock.");  // Producer releases the lock
    }

    public synchronized void consume() throws InterruptedException {
        // Wait for the producer to produce data
        while (!produced) {
            System.out.println("Consumer waiting for producer...");
            // wait is used when a thread wants to hold its execution until another thread to notify it.
            this.wait();
        }

        // Consume the data
        System.out.println("Consumer thread is consuming...");
    }
}
