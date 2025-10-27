package com.mylearnings.java.basicJava;

class CustomClassLoader extends java.lang.ClassLoader {
}

public class CustomClassLoaderExample {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CustomClassLoader classLoader = new CustomClassLoader();
        Class<?> customClass = classLoader.loadClass("com.mylearnings.java.basicJava.Person");
        Person ref = (Person) customClass.newInstance(); // Reflectively create an object
        System.out.println(ref.hashCode());
    }
}
