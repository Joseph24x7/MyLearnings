package com.mylearnings.java.leetcode150.sorting;

import java.util.Arrays;

public class MajorityElement {

    public static void main(String[] args) {
        MajorityElement majorityElement = new MajorityElement();
        System.out.println(majorityElement.majorityElement(new int[]{3, 2, 3}));
    }

    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int count = 1, maxCount = -1, maxElement = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }
            if (count > maxCount) {
                maxCount = count;
                maxElement = nums[i];
            }
        }

        return maxElement;

    }

}
