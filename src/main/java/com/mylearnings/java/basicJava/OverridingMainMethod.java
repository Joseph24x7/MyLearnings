package com.mylearnings.java.basicJava;

public class OverridingMainMethod {

    public static void main(String[] args) {
        Fruit f = new Fruit();
        f.main(args);
    }

}

class Fruit {

    public static void main(String[] args) {
        System.out.println("Fruit");
    }

}

class Apple extends Fruit {

    public static void main(String[] args) {
        System.out.println("Apple");
    }

}