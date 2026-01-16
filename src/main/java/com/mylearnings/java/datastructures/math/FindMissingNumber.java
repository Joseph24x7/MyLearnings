package com.mylearnings.java.datastructures.math;

public class FindMissingNumber {

    public static void main(String[] args) {
        FindMissingNumber missingNumber = new FindMissingNumber();
        System.out.println(missingNumber.findDuplicate(new int[]{1, 3, 4, 2, 2}));
    }

    private int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        // Phase 1: Detect cycle
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find cycle entry (duplicate)
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

}
