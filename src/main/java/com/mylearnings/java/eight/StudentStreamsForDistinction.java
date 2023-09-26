package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

public class StudentStreamsForDistinction {

    public static void main(String[] args) {

        List<Student> students = Arrays.asList(new Student("john", 85.0f), new Student("Ajay", 70.0f), new Student("Mani", 85.0f), new Student("Vikram", 60.0f));

        List<Student> student2s = students.stream().filter(s -> s.getMarks() >= 80.0f).map(s -> new Student(s.getName(), s.getMarks(), true)).toList();

        System.out.println(student2s);

    }

}

@Data
@AllArgsConstructor
class Student {

    private String name;
    private Float marks;
    private Boolean distinction;

    public Student(String name, Float marks) {
        super();
        this.name = name;
        this.marks = marks;
    }

}

