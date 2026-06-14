package com.mylearnings.java.datastructures.stringandintegers;

import java.util.LinkedList;
import java.util.Queue;

public class FirstNonRepeatingCharacter {

    public String FirstNonRepeating(String A) {
        int[] freq = new int[26];
        Queue<Character> q = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < A.length(); i++) {
            char ch = A.charAt(i);
            freq[ch - 'a']++;
            q.add(ch);
            
            // Remove characters from front of queue if they are repeating
            while (!q.isEmpty() && freq[q.peek() - 'a'] > 1) {
                q.poll();
            }
            
            // If queue is empty, no non-repeating character exists
            if (q.isEmpty()) {
                sb.append('#');
            } else {
                sb.append(q.peek());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        FirstNonRepeatingCharacter solver = new FirstNonRepeatingCharacter();
        String stream = "aabcbc";
        System.out.println("Stream: " + stream);
        System.out.println("First non-repeating output: " + solver.FirstNonRepeating(stream)); // Expected: "a#bb#c"
    }
}
