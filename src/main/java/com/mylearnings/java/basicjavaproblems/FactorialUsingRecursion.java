package com.mylearnings.java.basicjavaproblems;

public class FactorialUsingRecursion {

    public static void main(String[] args) {
        System.out.println(getFactorial(5));
    }

    private static int getFactorial(int i) {
        if(i==1) return 1;
        return i*getFactorial(i-1);
    }

}
