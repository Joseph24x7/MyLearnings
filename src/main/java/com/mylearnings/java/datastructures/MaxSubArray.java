package com.mylearnings.java.datastructures;

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

class Triplets {

    public static void main(String[] args) {

        int arr[] = {7, 8, 14, 15, 6, 7, 7};
        int finArr[] = new int[3];
        int requiredSum = 28;
        int currentSum = 0;
        int index = 0;

        for (int i=0; i<3; i++) {
            currentSum = currentSum + arr[i];
        }

        for(int i=1; i<arr.length-2; i++) {
            currentSum = currentSum - arr[i-1] + arr[i+3-1];
            if(requiredSum == currentSum) {
                index = i;
                break;
            }
        }

        for(int i=index,j=0; i<index+3; i++,j++) {
            finArr[j]=arr[i];
        }

        System.out.println(Arrays.toString(finArr));

    }

}
