package com.mylearnings.java.datastructures.leetcode150.greedy;

public class JumpGame1 {

    void main() {
        JumpGame1 jumpGame1 = new JumpGame1();
        System.out.println(jumpGame1.canJump(new int[]{2, 3, 1, 1, 4}));
    }

    public boolean canJump(int[] nums) {
        int index = 0;
        int maxJump = 0;
        while (index < nums.length) {
            if (index > maxJump) {
                return false;
            }
            maxJump = Math.max(maxJump, index + nums[index]);
            if (maxJump >= nums.length) {
                return true;
            }
            index++;
        }
        return true;

    }

}


