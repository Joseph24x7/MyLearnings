package com.mylearnings.java.datastructures;

public class FactorialUsingRecursion {

    public static void main(String[] args) {
        System.out.println(getFactorial(10));
    }

    private static int getFactorial(int i) {
        if(i<=1) return i;
        return i*getFactorial(i-1);
    }

}
