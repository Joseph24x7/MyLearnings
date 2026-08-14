package com.mylearnings.java.leetcode150.arrays;

public class HIndex {

    static void main() {

        HIndex obj = new HIndex();
        System.out.println(obj.hIndex(new int[]{3,0,6,1,5}));

    }

    public int hIndex(int[] citations) {

        int h = 1, max = -1;

        while (true) {

            int count = 0;

            for (int i : citations) {

                if (i >= h) {
                    count++;
                }

                if (count == h) {
                    break;
                }

            }

            if (count < h) {
                break;
            }
            h++;

        }

        return h - 1;


    }

}
