package com.mylearnings.java.collections;

import java.util.*;

public class VectorStackQueue {

    public static void main(String[] args) {

        // Vector Example
        Vector<String> vector = new Vector<>();
        vector.add("Apple");
        vector.add("Banana");
        vector.add("Cherry");
        System.out.println("Vector Elements:");
        Iterator<String> iterator = vector.iterator();
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            iterator.remove(); // Remove the current element using the iterator
            System.out.println(fruit);
        }

        // Queue - First-In-First-Out (FIFO)
        Queue<String> queue = new ArrayDeque<>();
        //Queue<String> queue2 = new PriorityQueue<>();
        queue.offer("One");
        queue.offer("Two");
        queue.offer("Three");
        System.out.println("\nQueue Elements:");
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

        // Stack - Last-In-First-Out (LIFO)
        Stack<String> stack = new Stack<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        System.out.println("\nStack Elements:");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }

    }
}
