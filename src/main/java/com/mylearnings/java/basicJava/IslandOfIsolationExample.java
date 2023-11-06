package com.mylearnings.java.basicJava;

public class IslandOfIsolationExample {
    public static void main(String[] args) {
        IslandObject obj1 = new IslandObject();
        IslandObject obj2 = new IslandObject();

        // Create a cyclic reference between obj1 and obj2
        obj1.setReference(obj2);
        obj2.setReference(obj1);

        // Nullify references to obj1 and obj2
        obj1 = null;
        obj2 = null;

        // The objects obj1 and obj2 in the code will not be collected by the garbage collector because they reference each other, creating an "Island of Isolation."
    }
}

class IslandObject {
    private IslandObject reference;

    public void setReference(IslandObject ref) {
        reference = ref;
    }
}

