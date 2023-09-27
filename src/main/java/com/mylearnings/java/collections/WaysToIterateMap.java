package com.mylearnings.java.collections;

import java.util.Map;

public class WaysToIterateMap {

    public static void main(String[] args) {

        Map<Integer, String> map = Map.of(1, "abc", 2, "bcd");

        // Using keySet() to iterate over keys
        for (Integer key : map.keySet()) {
            System.out.println("Key: " + key + ": value: " + map.get(key));
        }

        // Using a for-each loop to iterate over keys and values
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        //Using Java 8
        map.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));

    }


}
