package com.mylearnings.java.leetcode150.twopinters;

import java.util.Arrays;

public class RotateArray {

    public static void main() {
        RotateArray rotateArray = new RotateArray();
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        rotateArray.rotate(arr, 3);
        System.out.println(Arrays.toString(arr));
    }

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1); // [7, 6, 5, 4, 3, 2, 1]
        reverse(nums, 0, k - 1);  // [5, 6, 7, 4, 3, 2, 1]
        reverse(nums, k, nums.length - 1); // [5, 6, 7, 1, 2, 3, 4]
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

}
