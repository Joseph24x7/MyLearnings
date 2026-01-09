package com.mylearnings.java.java_code.advanced_java;

import java.util.stream.Collectors;

import static com.mylearnings.java.java_code.advanced_java.Java8BasicProblems.employees;

public class Java8GroupingByMapping {

    void main() {

        // Q1 - Employee grouping by ID problem but values is List<name>
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getId, Collectors.mapping(Employee::getName, Collectors.toList()))));

        // Group by Department & Value is List<Names>
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList()))));

    }

}
