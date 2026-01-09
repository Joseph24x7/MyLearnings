package com.mylearnings.java.leetcode150.twopinters;

public class IsSubsequence {

    public static void main(String[] args) {

        IsSubsequence isSubsequence = new IsSubsequence();
        System.out.println(isSubsequence.isSubsequence("abc", "ahbgdc")); //true
        System.out.println(isSubsequence.isSubsequence("axc", "ahbgdc")); //false
        System.out.println(isSubsequence.isSubsequence("aza", "abzba")); //true
        System.out.println(isSubsequence.isSubsequence("abbc", "ahbdc")); //false

    }

    public boolean isSubsequence(String s, String t) {

        if (s.length() == 0) {
            return true;
        }
        if (t.length() == 0) {
            return false;
        }

        int sStart = 0;
        int sEnd = s.length() - 1;

        int tStart = 0;
        int tEnd = t.length() - 1;

        while (sStart <= sEnd) {

            if ((sStart != sEnd && tStart == tEnd) || tStart > tEnd) {
                return false;
            }

            if (s.charAt(sStart) == t.charAt(tStart)) {
                sStart++;
            }
            tStart++;

            if (s.charAt(sEnd) == t.charAt(tEnd)) {
                sEnd--;
            }
            tEnd--;
        }

        return true;

    }

}
