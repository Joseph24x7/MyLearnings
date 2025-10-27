package com.mylearnings.java.datastructures.stringandintegers;

/**
 * O(n) time complexity
 **/
public class SumOfIntegers {

    public static void main(String[] args) {

        String s = "bq22s2c42c1111";
        int sum = 0, currentSum = 0;


        for (int i = 0; i < s.length(); i++) {
            try {
                int in = Integer.parseInt(s.charAt(i) + "");
                if (currentSum > 0) {
                    currentSum = currentSum * 10 + in;
                } else {
                    currentSum = currentSum + in;
                }
            } catch (Exception e) {
                sum = sum + currentSum;
                currentSum = 0;
            }
        }

        sum = sum + currentSum;
        System.out.println(sum);
    }
}
