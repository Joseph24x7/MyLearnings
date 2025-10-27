package com.mylearnings.java.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

public class FindTheUnsatisfiedCustomers {

    public static void main(String[] args) {
        System.out.println(findUnSatisfiedCustomers("abcdeffabcde", 3));
    }

    private static int findUnSatisfiedCustomers(String input, int noOfSeats) {
        Map<Character, String> room = new HashMap<>();
        int count = 0;
        int unSatisfiedCount = 0;
        for (int i = 0; i < input.length(); i++) {

            if (room.containsKey(input.charAt(i)) && count >= 3 && room.get(input.charAt(i)).equals("WAIT")) {
                room.remove(input.charAt(i)); //f
                unSatisfiedCount++;
            } else if (!room.containsKey(input.charAt(i)) && count >= 3) {
                room.put(input.charAt(i), "WAIT"); //def
            } else if (room.containsKey(input.charAt(i)) && room.get(input.charAt(i)).equals("ROOM")) {
                room.remove(input.charAt(i)); //abc
                count--;
            } else if (count < 3) {
                room.put(input.charAt(i), "ROOM"); //abc //de
                count++;
            }
        }
        return unSatisfiedCount;
    }

}
