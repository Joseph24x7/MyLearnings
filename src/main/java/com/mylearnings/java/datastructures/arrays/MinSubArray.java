package com.mylearnings.java.datastructures.arrays;

public class MinSubArray {


    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        System.out.println(minSubArrayLen(11, arr));
    }

    public static int minSubArrayLen(int target, int[] nums) {

        int start = 0, end = 0;
        int currentSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currentSum = currentSum + nums[i];
            if (currentSum >= target) {
                end = i + 1;
                break;
            }
        }

        int minSubArray = end - start;

        if (minSubArray > 0) {

            while (end < nums.length || currentSum >= target) {

                if (currentSum >= target) {
                    minSubArray = Math.min(minSubArray, end - start);
                    currentSum = currentSum - nums[start];
                    start++;

                } else if(end < nums.length) {
                    currentSum = currentSum + nums[end];
                    end++;
                }

            }

            if (currentSum >= target) {
                minSubArray = Math.min(minSubArray, end - start);
            }

        }
        return minSubArray;


    }
}
