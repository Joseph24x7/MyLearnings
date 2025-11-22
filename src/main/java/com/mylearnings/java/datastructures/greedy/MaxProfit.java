package com.mylearnings.java.datastructures.greedy;

public class MaxProfit {

    public int maxProfit(int[] prices) {
        int profit = 0;
        for(int i=prices.length-1; i>0; i--) {
            int prof = prices[i]-prices[i-1];
            if(prof>0) {
                profit += prof;
            }
        }
        return profit;
    }

    public static void main(String[] args) {
        MaxProfit obj = new MaxProfit();
        int[] prices = {7,6,4,3,1};
        System.out.println(obj.maxProfit(prices));
    }

}
