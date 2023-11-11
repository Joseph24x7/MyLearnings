package com.mylearnings.java.basicjavaproblems;

public class SecondMaxFromArray {

    public static void main(String[] args) {

        Integer[] arr = {10,3,4,4,9,8};
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        for (Integer integer : arr) {
            if (integer > max) {
                secondMax = max;
                max = integer;
            } else if (secondMax < integer && integer < max) {
                secondMax = integer;
            }
        }

        System.out.println(secondMax);

    }

}
