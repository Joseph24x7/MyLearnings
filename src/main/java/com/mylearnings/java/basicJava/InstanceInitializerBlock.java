package com.mylearnings.java.basicJava;

public class InstanceInitializerBlock {
    public static void main(String[] args) {
        InstanceInitializerBlock mc = new InstanceInitializerBlock();
    }

    // Instance initializer block
    // Code to be executed when an instance is created
    {
        Three three = new Three(); // One,Two,Three
    }
}

class One {
    public One() {
        System.out.print("One,");
    }
}

class Two extends One {
    public Two() {
        System.out.print("Two,");
    }
}

class Three extends Two {
    public Three() {
        System.out.print("Three");
    }
}
