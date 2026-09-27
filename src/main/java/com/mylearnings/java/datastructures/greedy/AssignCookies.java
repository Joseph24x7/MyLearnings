package com.mylearnings.java.datastructures;

import java.util.Arrays;

public class AssignCookies {

    public static int findContentChildren(int[] g, int[] s) {

        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0, j=0;

        int count = 0;

        while (i < g.length && j < s.length) {

            if(g[i] <= s[j]) {
                count++;
                i++;
                j++;
            } else {
                j++;
            }

        }

        System.out.println(count);

        return count;

    }

    static void main() {

        AssignCookies.findContentChildren(new int[]{10,9,8,7}, new int[]{5,6,7,8});

    }

}
