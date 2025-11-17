package com.mylearnings.java.datastructures.arrays;

import java.util.Arrays;

/** Sliding Window **/
public class MaxSubArray {

    public static void main(String[] args) {

        int arr[] = {7, 8, 14, 15, 6, 7, 7};
        int k = 3;

        int currentSum = 0;
        int maxSum = 0;
        for (int i=0; i<k; i++) {
            currentSum = currentSum + arr[i];
        }
        maxSum=  currentSum;

        for(int i=1; i<arr.length-k; i++) {

            currentSum = currentSum - arr[i-1] + arr[i+k-1];
            if(maxSum < currentSum) {
                maxSum = currentSum;
            }

        }

        System.out.println(maxSum);

    }

}