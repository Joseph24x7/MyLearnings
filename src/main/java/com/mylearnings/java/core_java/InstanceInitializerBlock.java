package com.mylearnings.java.core_java;

public class InstanceInitializerBlock {
    // Instance initializer block
    {
        // Code to be executed when an instance is created
        System.out.println("Instance initializer block executed");
    }
}

class MainClass {
    void main() {
        new InstanceInitializerBlock();
    }
}
