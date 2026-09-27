package com.mylearnings.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {

        if (intervals.length == 1) {
            return intervals;
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<List<Integer>> result = new ArrayList<>();

        int[] start = intervals[0];

        for (int i = 1; i < intervals.length; i++) {

            if (start[1] > intervals[i][0]) {
                start[1] = Math.max(start[1], intervals[i][1]);
            } else {
                result.add(List.of(start[0], start[1]));
                start = intervals[i];
            }

        }

        result.add(List.of(start[0], start[1]));

        int[][] arr = new int[result.size()][2];

        for(int i = 0; i < result.size(); i++) {
            arr[i] = new int[]{ result.get(i).get(0),  result.get(i).get(1)};
        }

        return arr;
    }

    static void main() {

        System.out.println(MergeIntervals.merge(new int[][]{{1, 7}, {2, 6}, {8, 10}, {15, 18}}));

    }

}
