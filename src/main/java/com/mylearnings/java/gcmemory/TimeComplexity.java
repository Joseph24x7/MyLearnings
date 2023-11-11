package com.mylearnings.java.gcmemory;

public class TimeComplexity {

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        // O(1) - Constant Time Complexity
        int firstElement = getFirstElement(array);
        System.out.println("First Element: " + firstElement);

        // O(N) - Linear Time Complexity
        System.out.println("All Elements:");
        printAllElements(array);

        // O(N^2) - Quadratic Time Complexity
        int[] unsortedArray = {5, 4, 3, 2, 1};
        bubbleSort(unsortedArray);
        System.out.println("Sorted Array:");
        printAllElements(unsortedArray);

        // O(log N) - Logarithmic Time Complexity
        int target = 3;
        int index = binarySearch(array, target);
        System.out.println("Index of " + target + ": " + index);
    }

    // O(1) - Constant Time Complexity
    static int getFirstElement(int[] array) {
        return array[0];
    }

    // O(N) - Linear Time Complexity
    static void printAllElements(int[] array) {
        for (int val : array) {
            System.out.println(val);
        }
    }

    // O(N^2) - Quadratic Time Complexity
    static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // O(log N) - Logarithmic Time Complexity
    static int binarySearch(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedArray[mid] == target) {
                return mid;
            } else if (sortedArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Target not found
    }
}

