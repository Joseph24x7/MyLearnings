package com.mylearnings.java.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

public class MaxNoOccurrenceOfWord {

    public static void main(String[] args) {

        String str = "I am doing doing doing good";
        String[] arr = str.split(" ");

        Map<String, Integer> count = new HashMap<>();

        for (String arrStr : arr) {

            if (count.containsKey(arrStr)) {

                count.put(arrStr, count.get(arrStr) + 1);

            } else {

                count.put(arrStr, 1);
            }
        }

        Integer max = 0;
        for (Map.Entry<String, Integer> map : count.entrySet()) {
            if (map.getValue() > max) {
                max = map.getValue();
            }
        }

        System.out.println(max);

    }

}
