package com.mylearnings.java.datastructures.stringandintegers;

/*Palindrome without using a new Memory*/
public class Palindrome {

    public static void main(String[] args) {

        String str = "Malayalam";
        System.out.println(isPalindromeOrNot(str.toLowerCase()));

    }

    private static boolean isPalindromeOrNot(String str) {
        int i = 0, j = str.length() - 1;
        while (i < j) {

            if (str.charAt(i) != str.charAt(j)) return false;
            i++;
            j--;
        }
        return true;

    }

}
