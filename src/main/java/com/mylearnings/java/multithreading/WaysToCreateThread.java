package com.mylearnings.java.multithreading;

import java.util.concurrent.*;

public class WaysToCreateThread {

    public static void main(String[] args) {

        // Useful simple, standalone thread that doesn't require sharing data or resources with other threads.
        // It's often used for tasks that can run independently.
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.setName("ThreadDemo");
        threadDemo.start();

        // This approach is suitable when you want to separate the task logic from the thread management.
        // It's often used when you have multiple tasks that can share the same thread.
        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread=new Thread(runnableDemo);
        thread.setName("RunnableDemo");
        thread.start();

        // Lambda expressions are a concise way to define small, one-off tasks without the need to define a separate class.
        Thread lambdaThread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("This is "+Thread.currentThread().getName()+" : "+i);
            }
        });
        lambdaThread.setName("MyLambdaThread");
        lambdaThread.start();

        // CompletableFuture is used when you need to perform asynchronous tasks that may return results or be composed together.
        // It's often used in scenarios where you want to create a chain of asynchronous operations.
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("This is " + Thread.currentThread().getName() + " : " + i);
            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("This is " + Thread.currentThread().getName() + " : " + i);
            }
        });
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);
        allOf.join();

        // Callable and ExecutorService are used when you need to execute a task that returns a result or throws an exception.
        // It's often used for tasks that require more control over thread management, resource allocation, and result handling.
        Callable<Integer> callableTask = () -> {
            int result = 0;
            for (int i = 0; i < 100; i++) {
                System.out.println("This is " + Thread.currentThread().getName() + " : " + i);
                result += i;
            }
            return result;
        };
        ExecutorService executorService = Executors.newFixedThreadPool(2); // Thread Pool concept
        Future<Integer> future = executorService.submit(callableTask);
        try {
            int result = future.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

}

class ThreadDemo extends Thread{

    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            System.out.println("This is "+Thread.currentThread().getName()+" : "+i);
        }
    }

}

class RunnableDemo implements Runnable {

    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            System.out.println("This is "+Thread.currentThread().getName()+" : "+i);
        }
    }

}
