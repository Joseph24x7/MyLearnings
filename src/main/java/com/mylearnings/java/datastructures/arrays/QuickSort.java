package com.mylearnings.java.datastructures.arrays;

public class QuickSort {

    public static void main(String[] args) {

        int arr[] = {13, 18, 27, 2, 19, 25};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(arr);

    }

    static void quickSort(int arr[], int start, int end) {

        if (start < end) {
            int p = partition(arr, start, end);  //p is partitioning index
            quickSort(arr, start, p - 1);
            quickSort(arr, p + 1, end);
        }
    }

    static int partition(int a[], int start, int end) {

        int pivot = a[end]; // pivot element
        int i = (start - 1);

        for (int j = start; j <= end - 1; j++) {
            // If current element is smaller than the pivot
            if (a[j] < pivot) {
                i++; // increment index of smaller element
                int t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        int t = a[i + 1];
        a[i + 1] = a[end];
        a[end] = t;
        return (i + 1);
    }

}
