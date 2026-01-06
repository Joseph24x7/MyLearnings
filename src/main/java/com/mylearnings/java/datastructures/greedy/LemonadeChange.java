package com.mylearnings.java.datastructures.greedy;

public class LemonadeChange {

    public static void main() {
        LemonadeChange lemonadeChange = new LemonadeChange();
        System.out.println(lemonadeChange.lemonadeChange(new int[]{5, 5, 5, 10, 20})); //true
        System.out.println(lemonadeChange.lemonadeChange(new int[]{5, 5, 10, 10, 20})); //false
    }

    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        boolean flag = true;
        for (int bill : bills) {

            switch (bill) {
                case 5 -> five++;
                case 10 -> {
                    if (five > 0) {
                        five--;
                        ten++;
                    } else {
                        flag = false;
                    }
                }
                default -> {
                    if (ten >= 1 && five >= 1) {
                        ten--;
                        five--;
                    } else if (five >= 3) {
                        five = five - 3;
                    } else {
                        flag = false;
                    }
                }
            }

            if (!flag) break;
        }
        return flag;
    }

}
