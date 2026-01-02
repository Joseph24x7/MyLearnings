package com.mylearnings.java.datastructures.leetcode150.greedy;

public class JumGame1 {

    public static void main(String[] args) {
        JumGame1 jumGame1 = new JumGame1();
        jumGame1.canJump(new int[]{1, 2, 1, 1, 1});
    }

    public boolean canJump(int[] nums) {

        int index = 0;
        int maxJump = 0;
        while (index < nums.length) {

            if (index > maxJump) {
                return false;
            }

            maxJump = Math.max(maxJump, index + nums[index]);

            if(maxJump >= nums.length) {
                return true;
            }

            index++;

        }

        return true;

    }

}


