package com.mylearnings.java.datastructures.leetcode150.greedy;

public class JumpGameII {

    public static void main(String[] args) {
        JumpGameII jumpGameII = new JumpGameII();
        System.out.println(jumpGameII.jump(new int[]{2, 1})); // 1
        System.out.println(jumpGameII.jump(new int[]{2, 3, 1, 1, 4})); // 2
    }

    public int jump(int[] nums) {

        if (nums.length <= 1) {
            return 0;
        }

        int index = 0;
        int jumps = 0;

        while (index < nums.length - 1) {
            int maxReach = 0;
            int nextIndex = index;

            for (int step = 1; step <= nums[index]; step++) {
                int pos = index + step;
                if (pos >= nums.length - 1) {
                    return jumps + 1;
                }

                int reach = pos + nums[pos];
                if (reach > maxReach) {
                    maxReach = reach;
                    nextIndex = pos;
                }
            }

            index = nextIndex;
            jumps++;
        }

        return jumps;
    }
}

