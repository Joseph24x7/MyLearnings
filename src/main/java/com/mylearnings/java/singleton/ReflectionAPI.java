/*package com.mylearnings.java.singleton;

import java.lang.reflect.Constructor;

public class ReflectionAPI {

    public static void main(String[] args) {
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

        System.out.println("instance2.hashCode():- " + instance2.hashCode());
    }

}

class Singleton {

    // public instance initialized when loading the class
    public static Singleton instance = new Singleton();

    private Singleton() {

        if (instance != null) {
            throw new RuntimeException();
        }
    }
}*/