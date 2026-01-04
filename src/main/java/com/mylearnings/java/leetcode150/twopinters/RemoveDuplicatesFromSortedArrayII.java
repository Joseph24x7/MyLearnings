package com.mylearnings.java.leetcode150.twopinters;

public class RemoveDuplicatesFromSortedArrayII {

    public static void main() {
        int[] nums = {1, 2, 3, 3};
        int length = removeDuplicates(nums);
        System.out.println("New length: " + length);
        for (int i = 0; i < length; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    public static int removeDuplicates(int[] nums) {
        int i = 2, index = 2;
        while (i < nums.length) {
            if (nums[i] != nums[index - 2]) {
                nums[index] = nums[i];
                index++;
            }
            i++;
        }
        return index;

    }

}
