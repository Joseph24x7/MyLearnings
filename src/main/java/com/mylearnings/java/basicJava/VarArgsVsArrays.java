package com.mylearnings.java.basicJava;

public class VarArgsVsArrays {

    public static void main(String... args) {

        //We can pass array and arguments to var arguments, but array accepts only array

        int result1 = sum(1, 2, 3);       // Passing three integers
        int result2 = sum(5, 10, 15, 20); // Passing four integers

        int[] array1 = {1, 2, 3};       // Creating an array with three integers
        int[] array2 = {5, 10, 15, 20}; // Creating an array with four integers

        int result3 = sum(array1);       // Passing the first array
        int result4 = sum(array2);       // Passing the second array

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
        System.out.println("Result 3: " + result3);
        System.out.println("Result 4: " + result4);
    }

    public static int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }

}
