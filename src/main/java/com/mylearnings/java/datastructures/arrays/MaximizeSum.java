package com.mylearnings.java.datastructures.arrays;

public class MaximizeSum {

    public int maximizeSum(int[] nums, int k) {

        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }

        int sum = 0;
        while(k > 0) {
            sum += max;
            max++;
            k--;
        }

        return sum;

    }

    public static void main(String[] args) {
        MaximizeSum solution = new MaximizeSum();
        int[] nums = {1, 2, 3, 4, 5};
        int k = 3;
        int result = solution.maximizeSum(nums, k);
        System.out.println("Maximized sum: " + result);
    }
}
