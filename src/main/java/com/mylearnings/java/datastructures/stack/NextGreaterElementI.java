package com.mylearnings.java.datastructures;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class NextGreaterElementI {

    public static int[] nextGreaterElement(int[] nums) {

        Deque<Integer> stack = new ArrayDeque<>();

        int[] result = new int[nums.length];

        int length = (nums.length * 2) - 1;
        while (length >= 0) {

            int val = nums[length % nums.length];
            while (!stack.isEmpty() && stack.peek() <= val) {
                stack.pop();
            }

            if (length < nums.length) {
                if (stack.isEmpty()) {
                    result[length] = -1;
                } else {
                    result[length] = stack.peek();
                }
            }

            stack.push(val);
            length--;
        }

        return result;
    }

    static void main() {

        System.out.println(Arrays.toString(NextGreaterElementI.nextGreaterElement(new int[]{1, 2, 3, 4, 3})));

    }


}


