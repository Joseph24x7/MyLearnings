package com.mylearnings.java.datastructures.matrix;

import java.util.ArrayList;
import java.util.List;

public class FindAdjacentElements {

    public static void main(String[] args) {

        int[][] arr = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };


        System.out.println(findAdjacentElements(arr, 11));
    }

    private static List<Integer> findAdjacentElements(int[][] arr, int index) {

        int left = 0, right = 0;

        boolean flag = false;

        outerLoop:
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == index) {
                    left = i;
                    right = j;
                    flag = true;
                    break outerLoop;
                }
            }
        }

        if (!flag) {
            System.out.println("Not Found");
            return null;
        }

        List<Integer> finalList = new ArrayList<>();

        computeTheAdjacent(finalList, left + 1, right, arr);
        computeTheAdjacent(finalList, left - 1, right, arr);
        computeTheAdjacent(finalList, left, right + 1, arr);
        computeTheAdjacent(finalList, left, right - 1, arr);

        return finalList;

    }

    private static void computeTheAdjacent(List<Integer> finalList, int left, int right, int[][] arr) {
        if (left >= 0 && left < arr.length && right>=0 && right < arr.length) {
            finalList.add(arr[left][right]);
        }
    }

}
