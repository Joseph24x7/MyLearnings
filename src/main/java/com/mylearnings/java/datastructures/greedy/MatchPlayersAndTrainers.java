package com.mylearnings.java.datastructures.greedy;

import java.util.Arrays;

public class MatchPlayersAndTrainers {

    public int matchPlayersAndTrainers(int[] players, int[] trainers) {
        Arrays.sort(players);
        Arrays.sort(trainers);
        int i = 0, j = 0, count = 0;
        while (i < players.length && j < trainers.length) {
            if (players[i] <= trainers[j]) {
                count++;
                i++;
            }
            j++;
        }
        return count;
    }

    public static void main(String[] args) {
        MatchPlayersAndTrainers solution = new MatchPlayersAndTrainers();
        int[] players = {4, 7, 9};
        int[] trainers = {8, 2, 5, 8};
        int result = solution.matchPlayersAndTrainers(players, trainers);
        System.out.println("Maximum matches: " + result);
    }

}
