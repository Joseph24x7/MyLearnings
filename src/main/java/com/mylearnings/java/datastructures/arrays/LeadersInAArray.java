package com.mylearnings.java.datastructures.arrays;

/**
 * O(n) time complexity
 **/
public class LeadersInAArray {

    void main() {

        int[] arr = {16, 17, 4, 3, 5, 2};
        int max = Integer.MIN_VALUE;
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        System.out.println(max);

    }

}
