package com.mylearnings.java.advanced_java;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import java.lang.Thread.UncaughtExceptionHandler;

public class Java11Features {

    void main() {

        // parallel stream with ForkJoinPool for Custom Thread Management
        parallelStreamWithCustomForkJoinPool();

    }

    private void parallelStreamWithCustomForkJoinPool() {

        long startTime = Instant.now().toEpochMilli();

        // parallel stream with ForkJoinPool for Custom Thread Management
        List<String> largeList = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z");

        // Uncaught exception handler
        ForkJoinPool customPool = getForkJoinPool();

        // Submit parallel stream work to this pool
        customPool.submit(() -> largeList.parallelStream().forEach(n -> System.out.println("Processing " + n + " in " + Thread.currentThread().getName()))).join();

        // Shutdown the pool
        customPool.shutdown();

        long endTime = Instant.now().toEpochMilli();
        System.out.println("Total Time Taken: " + (endTime - startTime) + " milliseconds");

    }

    private static ForkJoinPool getForkJoinPool() {

        // Custom Thread Factory for pool threads
        ForkJoinWorkerThreadFactory factory = pool -> {
            ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("Custom-FJ-Worker-" + worker.getPoolIndex());
            return worker;
        };

        // Uncaught Exception Handler
        UncaughtExceptionHandler handler = (t, e) -> System.out.println("Error in thread " + t.getName() + ": " + e.getMessage());

        // Saturation policy (runs when tasks cannot be scheduled)
        Predicate<? super ForkJoinPool> saturate = pool -> {
            System.out.println("Pool saturated! Queued tasks: " + pool.getQueuedTaskCount());
            return true; // true = try to compensate, false = deny
        };

        // Build advanced ForkJoinPool (Java 9+ constructor)
        return new ForkJoinPool(
                20,     // parallelism
                factory,         // thread factory
                handler,         // exception handler
                false,           // asyncMode
                20,              // corePoolSize  (>= parallelism)
                40,              // maximumPoolSize (>= corePoolSize)
                2,               // minimumRunnable
                saturate,        // saturation handler
                60L,             // keepAliveTime
                TimeUnit.SECONDS // unit
        );

    }

}
