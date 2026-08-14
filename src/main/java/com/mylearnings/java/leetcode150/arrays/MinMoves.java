package com.mylearnings.java.leetcode150.arrays;

import java.util.List;

public class MinMoves {

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 1, 1, 1, 0, 1, 0, 1);

        System.out.println(minMoves(list));
    }

    public static int minMoves(List<Integer> arr) {

        int countToLeft = 0;

        int start = 0;
        int end = arr.size() - 1;

        // Make all 0s come before all 1s
        while (start < end) {

            while (start < end && arr.get(start) == 0) {
                start++;
            }

            while (start < end && arr.get(end) == 1) {
                end--;
            }

            if (start < end) {
                // arr[start] = 1
                // arr[end]   = 0
                countToLeft++;
                start++;
                end--;
            }
        }

        int countToRight = 0;

        start = 0;
        end = arr.size() - 1;

        // Make all 1s come before all 0s
        while (start < end) {

            while (start < end && arr.get(start) == 1) {
                start++;
            }

            while (start < end && arr.get(end) == 0) {
                end--;
            }

            if (start < end) {
                // arr[start] = 0
                // arr[end]   = 1
                countToRight++;
                start++;
                end--;
            }
        }

        return Math.min(countToLeft, countToRight);
    }
}
