package com.mylearnings.java.datastructures.stack;

import java.util.*;

public class DailyTemperatures {

    static void main() {
        System.out.println(Arrays.toString(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
    }

    public static int[] dailyTemperatures(int[] temperatures) {

        Deque<Integer> stack = new ArrayDeque<>();

        int[] arr = new int[temperatures.length];
        for (int i = temperatures.length - 1; i >= 0; i--) {

            if (i == temperatures.length - 1) {
                arr[i] = 0;
                stack.push(i);
            } else {


                while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                    stack.pop();
                }

                if (!stack.isEmpty()) {
                    arr[i] = stack.peek() - i;
                }

                stack.push(i);

            }

        }

        return arr;

    }

}
