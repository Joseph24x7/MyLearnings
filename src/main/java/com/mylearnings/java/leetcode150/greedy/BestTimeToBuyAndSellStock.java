package com.mylearnings.java.leetcode150.greedy;

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        BestTimeToBuyAndSellStock bestTimeToBuyAndSellStock = new BestTimeToBuyAndSellStock();
        System.out.println(bestTimeToBuyAndSellStock.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    public int maxProfit(int[] prices) {
        int minProfit = Integer.MAX_VALUE, maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            minProfit = Math.min(minProfit, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minProfit);
        }
        return maxProfit;

    }


}
