package com.mylearnings.java.gcmemory;

public class HeapVsStack {

    public static void main(String[] args) {
        // Creating objects in heap memory
        // references will be in stack memory
        String str1 = new String("Hello, World!");
        String str2 = new String("Java is fun!");

        // When objects are no longer referenced, they can be garbage collected
        str1 = null; // This marks the first string for garbage collection

        // You can explicitly trigger garbage collection, but it's generally not recommended.
        // This means that calling System.gc() does not guarantee that the finalize() method of objects awaiting finalization will be called immediately.
        System.gc(); // Uncomment this line to request garbage collection.

        int x = 10; // Primitive data type, stored in stack memory
        int y = 20; // Another primitive data type
        int z = add(x, y); // Function call with parameters also stored in stack memory
        System.out.println("Result: " + z);
    }

    public static int add(int a, int b) {
        // Local variables 'a' and 'b' stored in the stack memory
        return a + b; // The result is also calculated in the stack memory
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Finalize");
    }

}
