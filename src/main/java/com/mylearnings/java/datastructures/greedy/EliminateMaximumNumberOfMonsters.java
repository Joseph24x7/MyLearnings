package com.mylearnings.java.datastructures.greedy;

import java.util.Arrays;

public class EliminateMaximumNumberOfMonsters {

    public static void main(String[] args) {
        System.out.println(new EliminateMaximumNumberOfMonsters().eliminateMaximum(new int[]{1, 3, 4}, new int[]{1, 1, 1}));
    }

    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        int[] time = new int[n];
        for (int i = 0; i < n; i++) {
            time[i] = (dist[i] - 1) / speed[i];
        }
        Arrays.sort(time);
        for (int i = 0; i < n; i++) {
            if (time[i] < i) {
                return i;
            }
        }
        return n;
    }

}
