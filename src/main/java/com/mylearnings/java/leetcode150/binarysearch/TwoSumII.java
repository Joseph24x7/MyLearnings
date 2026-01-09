package com.mylearnings.java.leetcode150.binarysearch;

import java.util.Arrays;

public class TwoSumII {

    public static void main(String[] args) {
        TwoSumII twoSumII = new TwoSumII();
        System.out.println(Arrays.toString(twoSumII.twoSum(new int[]{2, 7, 11, 15}, 9)));
    }

    public int[] twoSum(int[] numbers, int target) {

        int left = 0, right = numbers.length - 1;
        int[] arr = new int[2];
        while(left < right) {
            int sum = numbers[left] + numbers[right];
            if(sum == target) {
                arr[0] = left+1;
                arr[1] = right+1;
                break;
            } else if(sum > target) {
                right--;
            } else {
                left++;
            }

        }
        return arr;
    }

}
