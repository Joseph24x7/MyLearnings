package com.mylearnings.java.designpatterns.creational;


import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
class SimplePrototype implements Cloneable {

    private String name;
    private static SimplePrototype instance;

    public SimplePrototype() {
        // Initialize the instance only on the first creation
        if (instance == null) {
            this.name = "Original";
            instance = this;
        }
    }

    @Override
    public SimplePrototype clone() throws CloneNotSupportedException {
        SimplePrototype clonedInstance = (SimplePrototype) super.clone();
        return clonedInstance;
    }

}

// Step 3: Client code that uses the prototypes
public class PrototypeExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        SimplePrototype prototype = new SimplePrototype();
        SimplePrototype prototype2 = prototype.clone();
        System.out.println(prototype);
        System.out.println(prototype2);
    }
}

