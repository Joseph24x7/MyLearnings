package com.mylearnings.java.leetcode150.greedy;

public class BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockII bestTimeToBuyAndSellStock = new BestTimeToBuyAndSellStockII();
        System.out.println(bestTimeToBuyAndSellStock.maxProfit(new int[]{1,2,3,4,5}));
    }

    public int maxProfit(int[] prices) {
        int i = 1, profit = 0;
        while(i < prices.length) {
            if(prices[i-1] < prices[i]) {
                profit += prices[i] - prices[i-1];
            }
            i++;
        }
        return profit;
    }

}
