package com.mylearnings.java.datastructures.arrays;

import java.util.TreeMap;

public class Solution {

    public String sortString(String s) {

        TreeMap<Character, Integer> characterMap = new TreeMap<>();

        for (char c : s.toCharArray()) {
            characterMap.put(c, characterMap.getOrDefault(c, 0) + 1);
        }

        Character key = characterMap.firstKey();
        StringBuilder builder = new StringBuilder();

        // add first char
        builder.append(key);
        characterMap.put(key, characterMap.get(key) - 1);

        boolean forward = true;

        while (builder.length() < s.length()) {

            if (forward) {
                Character nextKey = characterMap.higherKey(key);
                if (nextKey == null) {
                    forward = false;
                    key = characterMap.lastKey();
                    continue;
                }
                if (characterMap.get(nextKey) > 0) {
                    builder.append(nextKey);
                    characterMap.put(nextKey, characterMap.get(nextKey) - 1);
                }
                key = nextKey;
            } else {
                Character prevKey = characterMap.lowerKey(key);
                if (prevKey == null) {
                    forward = true;
                    key = characterMap.firstKey();
                    continue;
                }
                if (characterMap.get(prevKey) > 0) {
                    builder.append(prevKey);
                    characterMap.put(prevKey, characterMap.get(prevKey) - 1);
                }
                key = prevKey;
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.sortString("aaaabbbbcccc")); // Output: abccbaabccba
    }
}
