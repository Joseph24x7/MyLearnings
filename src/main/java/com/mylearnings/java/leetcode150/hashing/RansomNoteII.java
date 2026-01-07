package com.mylearnings.java.leetcode150.hashing;

public class RansomNoteII {

    public static void main(String[] args) {
        RansomNoteII ransomNote = new RansomNoteII();
        System.out.println(ransomNote.canConstruct("abaa", "baaa"));
    }

    public boolean canConstruct(String ransomNote, String magazine) {

        if (ransomNote.length() != magazine.length()) return false;

        int[] count = new int[26];
        for (char c : magazine.toCharArray()) {
            count[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            if (--count[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

}
