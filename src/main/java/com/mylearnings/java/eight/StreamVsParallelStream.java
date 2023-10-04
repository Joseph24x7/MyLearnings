package com.mylearnings.java.eight;

import java.util.ArrayList;
import java.util.List;

public class StreamVsParallelStream {

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        // This method returns a sequential stream
        // which means that the operations performed on the stream are executed in a single thread, one after the other.
        numbers.stream().map(n -> n * 2).forEach(System.out::println);

        System.out.println("----");

        // This method returns a parallel stream
        // the operations performed on the stream can be executed concurrently in multiple threads.
        // It takes advantage of multicore processors to potentially speed up the processing of elements in a collection.

        // Thread pooling for parallel streams is managed by the ForkJoinPool, which is a specialized type of thread pool designed for parallelism.
        numbers.parallelStream().map(n -> n * 2).forEach(System.out::println);

    }
}
