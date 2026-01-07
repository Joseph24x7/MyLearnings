package com.mylearnings.java.leetcode150.hashing;

import java.util.HashMap;
import java.util.Map;

public class WordPattern {

    public static void main(String[] args) {
        WordPattern wordPattern = new WordPattern();
        System.out.println(wordPattern.wordPattern("abba", "dog cat cat dog")); // true
        System.out.println(wordPattern.wordPattern("abba", "dog cat cat fish")); // false
    }

    public boolean wordPattern(String pattern, String s) {

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();
        char[] arr = pattern.toCharArray();
        String[] strArr = s.split(" ");

        if (arr.length != strArr.length) {
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if(charToWord.containsKey(arr[i])) {
                if(!charToWord.get(arr[i]).equals(strArr[i])) {
                    return false;
                }
            } else {

                if(wordToChar.containsKey(strArr[i])) {
                    return false;
                }

                charToWord.put(arr[i], strArr[i]);
                wordToChar.put(strArr[i], arr[i]);
            }
        }

        return true;
    }

}
