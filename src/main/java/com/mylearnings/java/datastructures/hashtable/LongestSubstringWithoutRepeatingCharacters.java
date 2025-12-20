package com.mylearnings.java.datastructures.hashtable;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {

    void main() {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    public static int lengthOfLongestSubstring(String s) {

        if (s.isEmpty()) {
            return 0;
        }

        char[] arr = s.toCharArray();
        int start = 0, pointer = 0;

        int maxCount = -1;

        Set<Character> sets = new HashSet<>();

        while (pointer < arr.length) {

            if (!sets.contains(arr[pointer])) {
                sets.add(arr[pointer]);
                int count = pointer - start + 1;
                if (count > maxCount) {
                    maxCount = count;
                }
            } else {
                while (start < pointer) {
                    if (arr[start] != arr[pointer]) {
                        sets.remove(arr[start]);
                        start++;
                        int count = pointer - start + 1;
                        if (count > maxCount) {
                            maxCount = count;
                        }
                    } else {
                        start++;
                        int count = pointer - start + 1;
                        if (count > maxCount) {
                            maxCount = count;
                        }
                        break;
                    }
                }
            }
            pointer++;
        }

        return maxCount;

    }

}
