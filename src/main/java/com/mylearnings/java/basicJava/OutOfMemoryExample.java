package com.mylearnings.java.basicJava;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryExample {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        try {
            while (true) {
                list.add(new byte[1_000_000]); // Allocate 1MB of memory for each object
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError occurred!"); // cannot be handled
        }
    }
}
