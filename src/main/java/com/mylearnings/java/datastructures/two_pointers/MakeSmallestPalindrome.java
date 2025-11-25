package com.mylearnings.java.datastructures.two_pointers;

public class MakeSmallestPalindrome {

    public String makeSmallestPalindrome(String s) {

        char[] arr = s.toCharArray();

        int left = 0, right = arr.length - 1;
        while (left < right) {
            if (arr[left] != arr[right]) {
                if (arr[left] < arr[right]) {
                    arr[right] = arr[left];
                } else {
                    arr[left] = arr[right];
                }
            }

            left++;
            right--;
        }

        return String.valueOf(arr);

    }

    void main() {
        MakeSmallestPalindrome msp = new MakeSmallestPalindrome();
        String input = "egcfe";
        String result = msp.makeSmallestPalindrome(input);
        System.out.println("Smallest Palindrome: " + result);
    }
}
