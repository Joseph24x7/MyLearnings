package com.mylearnings.java.designpatterns.creational;

import java.io.*;
import java.lang.reflect.Constructor;

public class SingletonExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        // Trying to break with clone
        Singleton instanceOne = Singleton.instance;
        Singleton instanceTwo = (Singleton) instanceOne.clone();
        System.out.println("hashCode of instance 1 - " + instanceOne.hashCode());
        System.out.println("hashCode of instance 2 - " + instanceTwo.hashCode());

        // Trying to break with Serialization
        try {
            Singleton instance1 = Singleton.instance;
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream("file.text"));
            out.writeObject(instance1);
            out.close();

            // deserialize from file to object
            ObjectInput in = new ObjectInputStream(new FileInputStream("file.text"));

            Singleton instance2 = (Singleton) in.readObject();
            in.close();

            System.out.println("instance1 hashCode:- " + instance1.hashCode());
            System.out.println("instance2 hashCode:- " + instance2.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trying to break with Java Reflection API
        Singleton instance1 = Singleton.instance;
        Singleton instance2 = null;

        System.out.println("instance1.hashCode():- " + instance1.hashCode());

        try {
            Constructor[] constructors = Singleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                constructor.setAccessible(true);
                instance2 = (Singleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert instance2 != null;
        System.out.println("instance2.hashCode():- " + instance2.hashCode());
    }

}

class Singleton implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    public static Singleton instance = new Singleton();

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    //To overcome this issue, we need to override readResolve() method in the Singleton class and return the same Singleton instance
    @Serial
    protected Object readResolve() {
        return instance;
    }

}
