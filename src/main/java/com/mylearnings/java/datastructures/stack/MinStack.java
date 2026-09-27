package com.mylearnings.java;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class MinStack {

    Deque<Integer> stack;
    Deque<Integer> imag;

    public MinStack() {
        stack = new ArrayDeque<>();
        imag = new ArrayDeque<>();
    }

    public void push(int value) {

        if(stack.isEmpty()) {
            stack.addFirst(value);
            imag.addFirst(value);
        } else {
            stack.addFirst(value);
            int minValue = imag.getFirst();
            imag.addFirst(Math.min(value, minValue));
        }
    }

    public void pop() {
        stack.removeFirst();
        imag.removeFirst();
    }

    public int top() {
        return stack.getFirst();
    }

    public int getMin() {
        return imag.getFirst();
    }
}

class TesClass {

    static void main() {

        MinStack minStack = new MinStack();

        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);

        System.out.println(minStack.getMin());

        minStack.pop();

        System.out.println(minStack.top());

        System.out.println(minStack.getMin());

    }

}

