package com.mylearnings.java.datastructures.greedy;

import java.util.Arrays;

public class MinMovesToSeat {

    public int minMovesToSeat(int[] seats, int[] students) {

        Arrays.sort(seats);
        Arrays.sort(students);
        int totalMoves = 0;
        for (int i = 0; i < seats.length; i++) {
            totalMoves += Math.abs(seats[i] - students[i]);
        }
        return totalMoves;

    }

    void main() {
        MinMovesToSeat solution = new MinMovesToSeat();
        int[] seats = {4,1,5,9};
        int[] students = {1,3,2,6};
        int result = solution.minMovesToSeat(seats, students);
        System.out.println("Minimum moves required: " + result);
    }

}
