package com.mylearnings.java.collections;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public class VectorStackQueue {

    public static void main(String[] args) {

        // Vector Example
        Vector<String> vector = new Vector<>();
        vector.add("Apple");
        vector.add("Banana");
        vector.add("Cherry");
        System.out.println("Vector Elements:");
        for (String fruit : vector) {
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

        // Stack - Last-In-First-Out (FIFO)
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
