package com.mylearnings.java.datastructures.binarysearch;

public class FindElementInSortedArray {

    public static void main(String[] args) {

        int k = 31;
        int[] arr = {0, 1, 3, 5, 8, 11, 18, 21, 31, 51, 61};

        int begin = 0, end = arr.length-1;

        while (begin<end) {
            System.out.println(begin + ":" + end);
            int i = (begin + end) / 2;
            if (arr[i] == k) {
                System.out.println(i);
                break;
            } else if (arr[i] > k) {
                end = i-1;
            } else {
                begin = i+1;
            }

        }

    }

}
