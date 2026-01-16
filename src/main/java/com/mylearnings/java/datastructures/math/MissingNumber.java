package com.mylearnings.java.datastructures.math;

public class MissingNumber {

    private static int missingNumber(int[] nums) {

        int n = nums.length;

        int sum = n * (n + 1) / 2;

        int numsSum = 0;
        for (int num : nums) {
            numsSum += num;
        }

        return sum - numsSum;

    }

    public static void main(String[] args) {
        int[] arr = new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1};
        System.out.println(missingNumber(arr));
    }

}
