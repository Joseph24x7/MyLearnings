package com.mylearnings.java.leetcode150.hashing;

import java.util.HashMap;
import java.util.Map;

public class RansomNote {

    public static void main(String[] args) {
        RansomNote ransomNote = new RansomNote();
        System.out.println(ransomNote.canConstruct("abaa", "baaa"));
    }

    public boolean canConstruct(String ransomNote, String magazine) {

        Map<Character, Integer> magazineCount = new HashMap<>();

        char[] rArr = ransomNote.toCharArray();
        char[] mArr = magazine.toCharArray();

        if (rArr.length != rArr.length) return false;

        for (int i = 0; i < mArr.length; i++) {
            magazineCount.put(mArr[i], magazineCount.getOrDefault(mArr[i], 0) + 1);
        }

        System.out.println(magazineCount);

        for (int i = 0; i < rArr.length; i++) {
            if (magazineCount.containsKey(rArr[i])) {
                if (magazineCount.get(rArr[i]) == 0) {
                    return false;
                } else {
                    magazineCount.put(rArr[i], magazineCount.get(rArr[i]) - 1);
                }
            } else {
                return false;
            }
        }

        return true;

    }

}
