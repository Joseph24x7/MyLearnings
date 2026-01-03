package com.mylearnings.java.datastructures.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class NonOverlappingIntervals {

    public static void main(String[] args) {

        NonOverlappingIntervals nonOverlappingIntervals = new NonOverlappingIntervals();
        System.out.println(nonOverlappingIntervals.eraseOverlapIntervals(new int[][]{{0, 2}, {1, 3}, {2, 4}, {3, 5}, {4, 6}}));

    }

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int count = 0, i = 0, j = 1;
        while (j < intervals.length) {
            if (intervals[i][1] > intervals[j][0]) {
                count++;
                j++;
            } else {
                j++;
                i = j - 1;
            }

        }
        return count;
    }

}
