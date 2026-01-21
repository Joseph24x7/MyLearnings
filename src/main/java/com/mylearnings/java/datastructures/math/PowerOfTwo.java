package com.mylearnings.java.datastructures.math;

public class PowerOfTwo {

    public boolean isPowerOfTwo(int n) {
        if (n == 0) return false;
        if (n == 1) return true;
        return isPower(n);
    }

    private boolean isPower(int n) {
        if (n == 1) return true;
        if (n % 2 != 0) return false;
        return isPower(n / 2);
    }


    public static void main(String[] args) {
        PowerOfTwo powerOfTwo = new PowerOfTwo();
        System.out.println(powerOfTwo.isPowerOfTwo(16));
    }
    
}
