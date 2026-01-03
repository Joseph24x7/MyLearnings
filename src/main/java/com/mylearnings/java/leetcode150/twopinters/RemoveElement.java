package com.mylearnings.java.leetcode150.twopinters;

import java.util.Arrays;

public class RemoveElement {

    public static void main(String[] args) {
        RemoveElement obj = new RemoveElement();
        System.out.println("New length: " + obj.removeElement(new int[]{3, 2, 2, 3}, 3));
    }

    public int removeElement(int[] nums, int val) {

        int i = 0, index = 0;
        while(i < nums.length) {
            if(nums[i]!=val) {
                nums[index] = nums[i];
                index++;
            }
            i++;
        }

        return index;

    }

}
