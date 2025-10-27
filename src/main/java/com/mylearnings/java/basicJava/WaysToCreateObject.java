package com.mylearnings.java.basicJava;

import lombok.Data;

import java.io.IOException;

public class WaysToCreateObject {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Person obj = new Person(); // Creates an object of MyClass
        System.out.println(obj.hashCode());

        Person original = new Person();
        Person clone = (Person) original.clone(); // Creates a cloned object
        System.out.println(clone.hashCode());

        Class<?> clazz = Class.forName("com.mylearnings.java.basicJava.Person");
        Person ref = (Person) clazz.newInstance(); // Reflectively create an object
        System.out.println(ref.hashCode());

    }

}

@Data
class Person implements Cloneable {
    private String name;
    private int age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}