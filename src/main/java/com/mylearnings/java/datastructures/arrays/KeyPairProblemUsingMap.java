package com.mylearnings.java.datastructures.arrays;

import java.util.HashMap;
import java.util.Map;

public class KeyPairProblemUsingMap {

    public static void main(String[] args) {

        int[] arr = {1, 3, 7, 2, 9, 5};

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(11 - arr[i])) {
                System.out.println(i + ":" + map.get(11 - arr[i]));
                break;
            } else {
                map.put(arr[i], i);
            }
        }

    }

}
