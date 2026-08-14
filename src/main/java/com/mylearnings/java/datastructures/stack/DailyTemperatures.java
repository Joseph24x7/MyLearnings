package com.mylearnings.java.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DailyTemperatures {

    // Problem: Given an array of temperatures, return an array where each element is
    // the number of days until a warmer temperature. If no warmer day exists, put 0.
    // Input:  [73,74,75,71,69,72,76,73]
    // Output: [1, 1, 4, 2, 1, 1, 0, 0]
    // Approach: Monotonic decreasing stack — iterate right to left, store indices.

    public static void main(String[] args) {
        DailyTemperatures obj = new DailyTemperatures();
        System.out.println(Arrays.toString(
                obj.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})
        )); // [1, 1, 4, 2, 1, 1, 0, 0]
    }

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;
        int[] result = new int[n];

        Deque<Integer> stack = new ArrayDeque<>(); // stores indices

        for (int i = n - 1; i >= 0; i--) {

            // Pop indices whose temperature is <= current (not warmer)
            while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                stack.pop();
            }

            // Stack top now has the next warmer day index
            if (!stack.isEmpty()) {
                result[i] = stack.peek() - i;
            }

            stack.push(i);
        }

        return result;
    }

}
