package com.mylearnings.java.gcmemory;

import java.util.Arrays;

public class SpaceComplexity {

    public static void main(String[] args) {
        int n = 5;

        // O(1) - Constant Space Complexity
        int a = 5; // Uses a constant amount of memory.

        // O(N) - Linear Space Complexity
        int[] copy = new int[n]; // Uses memory proportional to 'n' for the 'copy' array.
        copy[0] = 5;

        System.out.println(a);
        System.out.println(Arrays.toString(copy));

    }

}
