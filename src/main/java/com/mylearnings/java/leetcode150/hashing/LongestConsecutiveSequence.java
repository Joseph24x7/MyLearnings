package com.mylearnings.java.leetcode150.hashing;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    /* Given an unsorted integer array, return the length of the longest sequence of consecutive integers.
            Input:  [100, 4, 200, 1, 3, 2]
    Output: 4
    Explanation: The longest consecutive sequence is [1, 2, 3, 4] */

    public static void main(String[] args) {

        System.out.println(findLongestConsSeq(new int[]{100, 4, 5, 6, 200, 1, 3, 2, 103, 102, 104, 107, 106, 105, 101}));
    }

    private static int findLongestConsSeq(int[] arr) {

        Set<Integer> sets = new HashSet<>();

        for (int num : arr) {
            sets.add(num); // 100, 4, 5, 6, 200, 1, 3, 2
        }

        int longest = 1;
        for (int i = 0; i < arr.length; i++) {

            int count = 1;

            if (!sets.contains(arr[i] - 1)) {  // 100

                int next = arr[i] + 1;
                while (sets.contains(next)) { // 101
                    count++;
                    next++;
                }

            }

            longest = Math.max(count, longest);

        }

        return longest;


    }

}
