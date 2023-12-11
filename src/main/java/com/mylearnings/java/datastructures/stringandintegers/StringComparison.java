package com.mylearnings.java.datastructures.stringandintegers;

public class StringComparison {

    public static void main(String[] args) {

        System.out.println(stringComparison("Abcd", "Abcs"));

    }

    static boolean stringComparison(String str1, String str2) {

        char[] ch1 = str1.toCharArray();
        char[] ch2 = str2.toCharArray();

        if (ch1.length != ch2.length) return false;

        for (int i = 0; i < ch1.length; i++) {

            if (ch1[i] != ch2[i]) return false;

        }

        return true;
    }

}
