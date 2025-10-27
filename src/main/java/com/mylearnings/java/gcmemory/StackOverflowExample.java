package com.mylearnings.java.gcmemory;

public class StackOverflowExample {

    public static void main(String[] args) {
        recursiveMethod();
    }

    public static void recursiveMethod() {
        recursiveMethod(); // This recursive call will eventually lead to a StackOverflowError.
    }

}
