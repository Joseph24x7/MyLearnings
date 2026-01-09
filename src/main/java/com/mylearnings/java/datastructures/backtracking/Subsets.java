package com.mylearnings.java.datastructures.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    public List<List<Integer>> subsets(int[] nums) {

        int index = 0;

        List<List<Integer>> finalList = new ArrayList<>();

        while (index <= nums.length) {

            if (index == 0) {
                finalList.add(new ArrayList<>());
            }

            int innerIndex = 0;
            while (innerIndex < nums.length) {

                List<Integer> lists = new ArrayList<>();
                int tempIndex = innerIndex;
                int endIndex = innerIndex + index + 1;

                while(tempIndex < endIndex) {
                    lists.add(nums[tempIndex]);
                    tempIndex++;
                }

                finalList.add(lists);
                innerIndex++;
            }

            index++;

        }

        return finalList;

    }

    public static void main(String[] args) {
        Subsets s = new Subsets();
        System.out.println(s.subsets(new int[]{1, 2, 3}));
    }

}
