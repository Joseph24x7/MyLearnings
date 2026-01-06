package com.mylearnings.java.leetcode150.greedy;

public class MajorityElement {

    public static void main(String[] args) {
        MajorityElement majorityElement = new MajorityElement();
        System.out.println(majorityElement.majorityElement(new int[]{2,2,1,1,1,2,2}));
    }

    private int majorityElement(int[] nums) {
        int count = 0, result = nums[0];
        for(int num : nums) {
            if(count==0) {
                result = num;
            }
            if(result == num) {
                count++;
            } else {
                count--;
            }
        }
        return result;
    }

}
