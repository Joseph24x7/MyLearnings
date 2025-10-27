package com.mylearnings.java.datastructures.arrays;

import java.util.Arrays;

/**
 * Input: arr[] = {10, 20, 10, 5, 15}
 * Output: prefixSum[] = {10, 30, 40, 45, 60}
 */
public class PrefixSum {

    public static void main(String[] args) {

        int arr[] = {10, 20, 10, 5, 15};

        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i] + arr[i - 1];
        }

        System.out.println(Arrays.toString(arr));
    }

}
