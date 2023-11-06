package com.mylearnings.java.basicJava;

public class OverridingFinalKeyword {
}

class Animal {

    final void hello() {
        System.out.println("Parent");
    }

}

class Dog extends Animal {

    // When you override a final method, it won't throw compilation exception
    /* final void hello() {
        System.out.println("Child");
    } */

}