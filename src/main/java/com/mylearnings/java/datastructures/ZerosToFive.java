package com.mylearnings.java.datastructures;

public class ZerosToFive {

    public static void main(String[] args) {
        System.out.println(convertZerosToFive(506000040)); // Convert to 556555545
    }

    private static int convertZerosToFive(int num) {

        if (num == 0) {
            return 5;
        }

        int ans = 0, factor = 1;

        while (num > 0) {

            if (num % 10 == 0) {
                ans = ans + (5 * factor);
            } else {
                ans = ans + ((num % 10) * factor);
            }

            factor = factor * 10;
            num = num / 10;
        }

        return ans;
    }

}
