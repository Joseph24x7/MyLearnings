package com.mylearnings.java.datastructures.binarysearch;

public class FindTransitionPoint {

    public static void main(String[] args) {

        int arr[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1};

        int begin = 0, end = arr.length-1;

        while (begin < end) {
            int i = (begin + end) / 2;
            if (arr[i] == 1 && arr[i - 1] == 0) {
                System.out.println(i - 1);
                break;
            } else if (arr[i] == 0 && arr[i + 1] == 1) {
                System.out.println(i);
                break;
            } else if (arr[i] == 0) {
                begin = i + 1;
            } else {
                end = i - 1;
            }

        }

    }
}
