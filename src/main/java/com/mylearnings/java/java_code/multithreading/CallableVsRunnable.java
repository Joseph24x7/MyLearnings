package com.mylearnings.java.java_code.multithreading;

import java.util.concurrent.*;

public class CallableVsRunnable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Runnable: You typically use Runnable when you want to perform a task in the
        // background without expecting a result or when you want to encapsulate code to run asynchronously.
        Runnable runnable = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Runnable Task: " + i);
                // throw new Exception("Exception"); // Not Possible
                // throw new RuntimeException("RuntimeException"); // Possible
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        // You typically use Callable when you need to execute a task concurrently
        // and receive a result or when you want to handle exceptions more flexibly.
        Callable<Integer> callableTask = () -> {
            int result = 0;
            for (int i = 1; i <= 5; i++) {
                result += i;
            }
            return result;
        };

        // This means that at any given time, there will be at most two threads actively executing tasks concurrently from the pool.
        // If you submit more than two tasks concurrently to the thread pool, the additional tasks will wait in a queue until
        // one of the active threads becomes available to execute them.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> futureTask = executorService.submit(callableTask);
        int result = futureTask.get();
        System.out.println("Callable Result: " + result);
        executorService.shutdown();

    }
}
