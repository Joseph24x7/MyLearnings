package com.mylearnings.java.datastructures.stack;

import java.util.Stack;

public class StackBrackets {

    void main() {
        String str = "()";
        System.out.println(isBracketsValid(str));
    }

    private static boolean isBracketsValid(String str) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (ch == ']') {

                if (stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (ch == '}') {

                if (stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (ch == ')') {

                if (stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }

            } else {
                stack.push(ch);
            }

        }

        return stack.empty();

    }

}
