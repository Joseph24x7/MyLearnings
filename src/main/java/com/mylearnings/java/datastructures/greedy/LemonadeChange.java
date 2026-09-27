package com.mylearnings.java.datastructures;

public class LemonadeChange {

    static void main() {

        System.out.println(LemonadeChange.lemonadeChange(new int[]{5, 5, 10, 20, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 20, 5, 20, 5}));

    }

    public static boolean lemonadeChange(int[] bills) {

        int five = 0, ten = 0, twen = 0;
        for (int bill : bills) {

            if (bill == 5) {
                five++;
            } else if (bill == 10) {

                if (five > 0) {
                    five--;
                } else {
                    return false;
                }

                ten++;

            } else if (bill == 20) {

                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }

                twen++;

            }

        }

        return true;
    }

}
