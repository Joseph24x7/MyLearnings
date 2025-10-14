package com.mylearnings.java.datastructures.string;

public class NumberOfChangingKeys {

    public static int countKeyChanges(String s) {
        s = s.toLowerCase();
        int count = 0;
        if (s.length() > 1) {
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) != s.charAt(i-1)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countKeyChanges("aAbBcC"));
    }

}
