package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class FirstThreeStudentsWithMoreMarks {

    public static void main(String[] args) {

        List<Employee> students = List.of(new Employee(1, 100), new Employee(2, 80),
                new Employee(3, 85), new Employee(4, 92), new Employee(5, 50));

        List<Employee> sortedStudentsFirstThree = students.stream().sorted((s1, s2) -> s2.getMarks() - s1.getMarks())
                .limit(3).skip(0)
                .toList();

        sortedStudentsFirstThree.forEach(System.out::println);
    }

}

@AllArgsConstructor
@Data
class Employee {

    private Integer id;
    private Integer marks;

}
