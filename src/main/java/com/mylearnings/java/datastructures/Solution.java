package com.mylearnings.java.datastructures;

import java.util.Arrays;

public class Solution {
    public int pivotInteger(int n) {

        if (n == 1) {
            return 1;
        }

        int[] forward = new int[n];
        int[] backward = new int[n];

        int index = 0;
        forward[index] = 1;
        backward[index] = n;
        index++;

        int f = 2, b = n - 1;
        while (index < n) {
            forward[index] = forward[index - 1] + f;
            backward[index] = backward[index - 1] + b;
            index++;
            f++;
            b--;
        }

        int start = 0, end = n - 1;
        while (start < end) {
            if (backward[start] == forward[end]) {
                return end + 1;
            }
            start++;
            end--;
        }


        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.pivotInteger(3));
    }
}
