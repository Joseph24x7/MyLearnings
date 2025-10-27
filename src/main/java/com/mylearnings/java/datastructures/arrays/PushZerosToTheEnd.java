package com.mylearnings.java.datastructures.arrays;

import java.util.Arrays;

/**
 * O(n) time complexity
 * Two Pointers
 **/
public class PushZerosToTheEnd {

    public static void main(String[] args) {

        int[] arr = {1, 2, 0, 1, 0, 1, 3, 5, 6, 0, 8};

        int i = 0, j = arr.length - 1;

        while (i < j) {

            if (arr[i] == 0 && arr[j] != 0) {
                arr[i] = arr[j];
                arr[j] = 0;
                i++;
                j--;
            } else if (arr[i] != 0) {
                i++;
            } else if (arr[j] == 0) {
                j--;
            }

        }

        System.out.println(Arrays.toString(arr));

    }

}

/**
 * O(n) time complexity
 **/
class PushNegativeToTheLeft {

    public static void main(String[] args) {

        int[] arr = {-1, 0, 5, -1, 3, -5, 6, 0, -8};
        int i = 0, j = arr.length - 1;

        while (i < j) {

            if (arr[i] >= 0 && arr[j] < 0) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                j--;
                i++;
                continue;
            }

            if (arr[i] < 0) {
                i++;
            }

            if (arr[j] >= 0) {
                j--;
            }
        }

        System.out.println(Arrays.toString(arr));


    }

}