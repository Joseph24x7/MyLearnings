package com.mylearnings.java.leetcode150.hashing;

import java.util.*;

public class GroupAnagrams {

    /*
     * LeetCode 49 - Group Anagrams
     * Given an array of strings, group the anagrams together.
     *
     * Input:  ["eat","tea","tan","ate","nat","bat"]
     * Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
     *
     * Time:  O(N * K log K)  — N = number of strings, K = max string length
     * Space: O(N * K)
     */

    public static void main(String[] args) {
        GroupAnagrams obj = new GroupAnagrams();
        System.out.println(obj.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        // Output: [[eat, tea, ate], [tan, nat], [bat]]
    }

    // ── Approach 1: Sorted key (simpler, interview-friendly) ──────────────────
    // Sort each word → use sorted string as map key.
    // "eat", "tea", "ate" all sort to "aet" → same group.
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);                         // sort chars: "eat" → "aet"
            String key = new String(chars);             // key = "aet"

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

    // ── Approach 2: Frequency-map key (O(K) per word, no sort) ────────────────
    // Use character frequency array as key. Avoids sorting.
    public List<List<String>> groupAnagramsFreq(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            int[] freq = new int[26];
            for (char ch : str.toCharArray()) {
                freq[ch - 'a']++;
            }
            String key = Arrays.toString(freq);         // e.g. "[1,0,0,0,1,0,...,1,0]"
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }

        return new ArrayList<>(map.values());
    }

}

