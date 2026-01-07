package com.mylearnings.java.leetcode150.hashing;

import java.util.HashSet;
import java.util.Set;

public class HappyNumber {

    public static void main(String[] args) {
        HappyNumber sol = new HappyNumber();
        System.out.println(sol.isHappy(19)); //true
        System.out.println(sol.isHappy(2)); //false
    }

    public boolean isHappy(int n) {

        Set<Integer> integers = new HashSet<>();

        while (true) {

            if (integers.contains(n)) {
                return false;
            }

            integers.add(n);

            int squares = 0;
            while (n > 0) {
                squares = squares + (n % 10) * (n % 10);
                n = n / 10;
            }

            if (squares == 1) {
                return true;
            } else {
                n = squares;
            }

        }

    }

}
