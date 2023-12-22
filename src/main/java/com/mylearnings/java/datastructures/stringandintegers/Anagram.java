package com.mylearnings.java.datastructures.stringandintegers;

public class Anagram {

    public static void main(String[] args) {
        System.out.println(isAnagram("rat", "car"));
    }

    public static boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) return false;

        int[] arr = new int[26]; // If it is only alphabets

        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            arr[t.charAt(i) - 'a']--;
        }

        for (int j : arr) {
            if (j > 0) return false;
        }

        return true;

        /* Map<Character, Integer> chars = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            if (chars.containsKey(t.charAt(i))) {
                chars.put(t.charAt(i), chars.get(t.charAt(i)) + 1);
            } else {
                chars.put(t.charAt(i), 1);
            }
        }

        System.out.println(chars);

        for (int i = 0; i < s.length(); i++) {
            if (chars.containsKey(s.charAt(i))) {
                if(chars.get(s.charAt(i))-1 == 0) {
                    chars.remove(s.charAt(i));
                } else {
                    chars.put(s.charAt(i), chars.get(s.charAt(i)) - 1);
                }
            } else {
                return false;
            }
        }

        if(chars.isEmpty()) return true;

        return false;

        */


    }

}
