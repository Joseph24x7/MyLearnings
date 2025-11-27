package com.mylearnings.java.datastructures.greedy;

import java.util.Arrays;

public class FindLucky {

    public int findLucky(int[] arr) {
        // return solution1(arr);
        return Solution2(arr);
    }

    private int Solution2(int[] arr) { // O(2n)

        int[] arrCount = new int[500];
        for (int val : arr) {
            arrCount[val - 1]++;
        }
        int maxLuckyNum = -1;
        for (int val : arr) {
            if(arrCount[val - 1] == val){
                if(val > maxLuckyNum){
                    maxLuckyNum = val;
                }
            }
        }
        return maxLuckyNum;

    }


    private int solution1(int[] arr) {

        Arrays.sort(arr); // O(n log n)

        int index = 0;
        int luckNum = -1;
        for (int i = 0; i < arr.length; i++) { // O(n)

            if (i > 0 && arr[i] != arr[i - 1]) {
                index = 1;
            } else {
                index++;
            }

            if (index == arr[i] && (i >= arr.length - 1 || arr[i] != arr[i + 1])) {
                if (arr[i] > luckNum) {
                    luckNum = arr[i];
                }
            }
        }

        return luckNum;

    }

    public static void main(String[] args) {
        FindLucky fl = new FindLucky();
        int[] arr = {4, 3, 2, 2, 4, 1, 3, 4, 3};
        int result = fl.findLucky(arr);
        System.out.println("Lucky Number: " + result);
    }

}
