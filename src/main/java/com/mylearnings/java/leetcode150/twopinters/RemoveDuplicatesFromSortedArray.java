package com.mylearnings.java.leetcode150.twopinters;

public class RemoveDuplicatesFromSortedArray {

    public static void main() {
        RemoveDuplicatesFromSortedArray obj = new RemoveDuplicatesFromSortedArray();
        System.out.println("New length: " + obj.removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
    }

    public int removeDuplicates(int[] nums) {
        int i = 1, index = 1;
        while (i < nums.length) {
            if (nums[i] != nums[index-1]) {
                nums[index] = nums[i];
                index++;
            }
            i++;
        }
        return index;
    }

}
