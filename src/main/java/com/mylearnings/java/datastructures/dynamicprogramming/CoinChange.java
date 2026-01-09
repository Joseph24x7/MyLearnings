package com.mylearnings.java.datastructures.dynamicprogramming;

import java.util.Arrays;

public class CoinChange {

    void main() {

        CoinChange obj = new CoinChange();
        int[] coins = {1,2,5};
        int amount = 11;
        int result = obj.coinChange(coins, amount);
        System.out.println("Minimum coins needed: " + result);

    }

    public int coinChange(int[] coins, int amount) {

        if (amount == 0) {
            return 0;
        }

        Arrays.sort(coins);

        int index = coins.length - 1;
        int count = 0;

        while (index >= 0) {

            if (amount > 0) {
                amount = amount - coins[index];
                count++;
            }

            if(amount == 0) {
                return count;
            }

            if (amount < 0) {
                amount = amount + coins[index];
                count--;
                index--;
            }

        }

        return -1;



    }

}
