package com.mylearnings.java.advanced_java;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Java9AndAboveFeatures {

    public static void main(String[] args) {

        //Java 9 - Immutable Collection. List.of(), Set.of(), Map.of()
        List<String> immutableList = List.of("Apple", "Banana", "Cherry", "Cherry");
        System.out.println(immutableList);

        //Java 10 - Immutable Collection. List.copyOf(list); - creates a immutable collection from a mutable collection.
        List<String> originalList = new ArrayList<>();
        originalList.add("Apple");
        originalList.add("Banana");
        originalList.add("Cherry");
        List<String> immutableList2 = List.copyOf(originalList);
        System.out.println(immutableList2);

        //Java 12 - Switch Expressions: Use switch as an expression rather than a statement, making code more concise.
        int dayOfWeek = 3;
        String dayName = switch (dayOfWeek) {
            case 3 -> "Wednesday";
            case 4 -> "Thursday";
            default -> "Invalid day";
        };
        System.out.println("It's " + dayName);

        //Java 13 - Text Blocks: Text blocks will simplify the part of writing multiline strings.
        String html = """
                    <html>
                        <body>
                            <p>Hello, world!</p>
                        </body>
                    </html>
                """;
        System.out.println(html);

        //Java 14 - Records (Preview Feature) - Concise way to declare classes that are mainly used for data storage.
        Person person = new Person("Alice", 30);
        Person anotherPerson = new Person("Alice", 30);
        boolean isEqual = person.equals(anotherPerson);
        System.out.println("Are they equal? " + isEqual);

        //Java 14 - Pattern matching for instanceof simplifies code for type checking and casting.
        Object obj = "Hello, Java 16";
        if (obj instanceof String str) {
            System.out.println("Length of the string: " + str.length());
        } else {
            System.out.println("Not a string");
        }

        //Java 15 - Sealed classes: Allows you to control which classes can extend or implement them.
        Shape circle = new Circle(10d);
        Shape triangle = new Triangle(10d, 10d, 10d);
        System.out.println(circle);
        System.out.println(triangle);

        Object obj2 = "Hello, Java 17";

    }

}

    // Defining a record class
    record Person(String name, int age) {
    }

sealed

class Shape permits Circle, Triangle {
    // Common properties and methods for all shapes
}

@AllArgsConstructor
@Getter
final class Circle extends Shape {
    private final double radius;
}

@AllArgsConstructor
@Getter
non-sealed

class Triangle extends Shape {
    private final double side1;
    private final double side2;
    private final double side3;
}