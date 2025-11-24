package com.mylearnings.java.datastructures.math;

public class MinMaxDifference {

    public int minMaxDifference(int num) {
        String numStr = Integer.toString(num);
        int i=0;
        char firstChar = 0;
        while(i < numStr.length()) {
            firstChar = numStr.charAt(i);
            if (firstChar != '9') {
                break;
            }
            i++;
        }

        int maxNum = Integer.parseInt(numStr.replace(String.valueOf(firstChar), "9"));
        firstChar = numStr.charAt(0);
        int minNum = Integer.parseInt(numStr.replace(firstChar, '0'));
        return maxNum - minNum;
    }

    public static void main(String[] args) {
        MinMaxDifference solution = new MinMaxDifference();
        int num = 90;
        int result = solution.minMaxDifference(num);
        System.out.println("Minimum and maximum difference: " + result);
    }
}
