package com.mylearnings.java.datastructures;

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

        for (int i = 0; i < arr.length; i++) {
            if (hashSet.contains(k - arr[i])) {
                System.out.println("True");
                break;
            } else {
                hashSet.add(arr[i]);
            }
        }
    }

}
