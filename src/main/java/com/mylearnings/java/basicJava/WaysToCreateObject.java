package com.mylearnings.java.basicJava;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class WaysToCreateObject {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // Using constructor
        Person obj = new Person();
        System.out.println(obj.hashCode());

        // Creates a cloned object
        Person original = new Person();
        Person clone = (Person) original.clone();
        System.out.println(clone.hashCode());

        // Reflectively create an object
        Class<?> clazz = Class.forName("com.mylearnings.java.basicJava.Person");
        Person ref = (Person) clazz.newInstance();
        System.out.println(ref.hashCode());

        // Using Dependency Injection / Frameworks
        ApplicationContext context = new AnnotationConfigApplicationContext(Person.class);
        Person springObj = context.getBean(Person.class);

        // Using the Builder Pattern
        Person p = Person.builder().name("John").age(30).build();

    }

}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Person implements Cloneable {
    private String name;
    private int age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}