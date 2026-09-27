package com.mylearnings.java.datastructures;

public class JumpGameI {

    static void main() {

        System.out.println(JumpGameI.canJump(new int[]{1,1,1,0}));

    }

    public static boolean canJump(int[] nums) {

        int index = 0;
        int maxIndex = 0;

        if(nums.length == 1) {
            return true;
        }

        while (index < nums.length) {

            if (maxIndex >= nums.length - 1) {
                return true;
            }

            if(nums[index] == 0 && maxIndex <= index) {
                return false;
            }

            maxIndex = Math.max(maxIndex, index + nums[index]);

            index++;

        }

        return false;


    }

}
