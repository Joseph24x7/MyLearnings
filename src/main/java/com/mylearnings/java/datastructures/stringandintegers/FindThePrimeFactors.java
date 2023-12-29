package com.mylearnings.java.datastructures.stringandintegers;

public class FindThePrimeFactors {

    public static void main(String[] args) {
        findThePrimeFactors(315);
    }

    private static void findThePrimeFactors(int input) {

        int i = 2;
        while (i <= input) {
            if (input % i == 0 && findIfItIsPrime(i)) {
                System.out.println(i); // Prime Factors - 3, 3, 5, 7
                input = input / i;
            } else {
                i++;
            }
        }
    }

    private static boolean findIfItIsPrime(int factor) {
        if (factor <= 1) {
            return false;
        } else if (factor <= 3) {
            return true;
        } else if (factor % 2 == 0 || factor % 3 == 0) {
            return false;
        }

        int i = 5;
        while (i * i <= factor) {
            if (factor % i == 0 || factor % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }

        return true;
    }

}
