package com.mylearnings.java.leetcode150.slidingwindow;

public class MinimumSizeSubarraySum {

    public static void main(String[] args) {

        MinimumSizeSubarraySum minimumSizeSubarraySum = new MinimumSizeSubarraySum();
        System.out.println(minimumSizeSubarraySum.minSubArrayLen(11, new int[]{1,2,3,4,5}));

    }

    public int minSubArrayLen(int target, int[] nums) {
        int index = 0, startIndex = 0, minIndex = Integer.MAX_VALUE, sum = 0;
        while (index < nums.length) {
            sum = sum + nums[index];
            if(sum >= target) {
                while(sum >= target) {
                    minIndex = Math.min(minIndex , index - startIndex + 1);
                    sum = sum - nums[startIndex];
                    startIndex++;
                }
            }
            index++;
        }
        return minIndex == Integer.MAX_VALUE ? 0 : minIndex;
    }

}
