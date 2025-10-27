package com.mylearnings.java.basicJava;

public class OverridingStaticKeyword {

    public static void main(String[] args) {

        Parent p = new Child();
        p.hello();
    }

}

class Parent {

    static void hello() {
        System.out.println("Parent");
    }

}

class Child extends Parent {

    // When you override a static method, it won't throw any compilation / runtime error, rather it will hide the child method
    static void hello() {
        System.out.println("Child");
    }

}