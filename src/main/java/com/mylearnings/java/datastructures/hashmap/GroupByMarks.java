package com.mylearnings.java.datastructures.hashmap;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class GroupByMarks {

    public static void main(String[] args) {

        List<Student> students = Arrays.asList(new Student("john", 85.0f), new Student("Ajay", 70.0f),
                new Student("Mani", 85.0f), new Student("Vikram", 60.0f));

        Map<Float, List<Student>> groupByMark = new HashMap<>();

        for(Student student : students) {
            List<Student> studentList;
            if (groupByMark.containsKey(student.getMarks())) {
                studentList = groupByMark.get(student.getMarks());
            } else {
                studentList = new ArrayList<>();
            }
            studentList.add(student);
            groupByMark.put(student.getMarks(), studentList);
        }

        System.out.println(groupByMark);

        System.out.println(students.stream().collect(Collectors.groupingBy(Student::getMarks)));
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
