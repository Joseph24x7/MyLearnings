package com.mylearnings.java.datastructures.arrays;

import java.util.HashSet;
import java.util.Set;

/**
 * O(n) Time Complexity
 * O(n) Space Complexity
 */
public class KeyPairProblem {

    public static void main(String[] args) {

        int[] arr = {1, 2, 4, 5, 4, 6, 6, 9, 11, 33};
        int k = 16;

        Set<Integer> hashSet = new HashSet<>();

        for (int j : arr) {
            if (hashSet.contains(k - j)) {
                System.out.println("True");
                break;
            } else {
                hashSet.add(j);
            }
        }
    }

}
