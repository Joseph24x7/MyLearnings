package com.mylearnings.java.leetcode150.hashing;

public class IsomorphicStringsII {

    public static void main(String[] args) {
        IsomorphicStringsII sol = new IsomorphicStringsII();
        System.out.println(sol.isIsomorphic("egg", "add"));     // true
        System.out.println(sol.isIsomorphic("foo", "bar"));     // false
        System.out.println(sol.isIsomorphic("paper", "title")); // true
        System.out.println(sol.isIsomorphic("ab", "aa"));       // false
    }

    public boolean isIsomorphic(String s, String t) {

        if (s.length() != t.length()) return false;

        int[] mapST = new int[256];
        int[] mapTS = new int[256];

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);

            if (mapST[c1] != mapTS[c2]) {
                return false;
            }

            mapST[c1] = i + 1;
            mapTS[c2] = i + 1;
        }

        return true;
    }

}
