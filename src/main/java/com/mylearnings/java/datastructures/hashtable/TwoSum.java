package com.mylearnings.java.datastructures.hashtable;

import java.util.*;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> maps = new HashMap<>();
        int[] arr = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (maps.containsKey(target - nums[i])) {
                arr[0] = i;
                arr[1] = maps.get(target - nums[i]);
                break;
            } else {
                maps.put(nums[i], i);
            }
        }
        return arr;
    }

    void main() {
        TwoSum obj = new TwoSum();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = obj.twoSum(nums, target);
        System.out.println("Indices: " + result[0] + ", " + result[1]);
    }
}
