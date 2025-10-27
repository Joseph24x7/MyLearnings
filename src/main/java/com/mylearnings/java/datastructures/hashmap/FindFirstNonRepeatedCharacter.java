package com.mylearnings.java.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindFirstNonRepeatedCharacter {

    public static void main(String[] args) {

        //Given a String, find the first non-repeated character in it using Stream functions?
        //"Java is good Awesome language"

        String str = "Lava is good Awesome Language";

        // Using Java 8
        Map<Character, Long> characters = str.chars().mapToObj(c -> (char) c).filter(i -> i != ' ').collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(str.chars().mapToObj(c -> (char) c).filter(k -> characters.get(k) == 1L).findFirst().orElse(' '));

        // Using Java 7
        Map<Character, Integer> chars = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (ch != ' ') {
                if (chars.containsKey(ch)) {
                    chars.put(ch, chars.get(ch) + 1);
                } else {
                    chars.put(ch, 1);
                }
            }
        }

        System.out.println(characters);
        System.out.println(chars);

        for (int i = 0; i < str.length(); i++) {
            if (chars.get(str.charAt(i)) == 1) {
                System.out.println(str.charAt(i));
                break;
            }

        }

    }

}
