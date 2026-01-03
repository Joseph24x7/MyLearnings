package com.mylearnings.java.leetcode150.twopinters;

import java.util.Arrays;

public class MergeSortedArray {

    public static void main(String[] args) {
        MergeSortedArray obj = new MergeSortedArray();
        int[] nums1 = new int[]{0};
        obj.merge(nums1, 0, new int[]{1}, 1);
        System.out.println("Merged Array: " + Arrays.toString(nums1));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1;
        int index = m + n - 1;
        while (index>=0) {
            if(i>=0 && j>=0) {
                if (nums2[j] > nums1[i]) {
                    nums1[index] = nums2[j];
                    j--;
                } else {
                    nums1[index] = nums1[i];
                    i--;
                }
            } else if(i>=0) {
                break;
            } else {
                nums1[index] = nums2[j];
                j--;
            }
            index--;
        }
    }

}
