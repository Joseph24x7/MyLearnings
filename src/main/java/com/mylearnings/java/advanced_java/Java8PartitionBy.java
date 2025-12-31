package com.mylearnings.java.advanced_java;

import java.util.stream.Collectors;

import static com.mylearnings.java.advanced_java.Java8BasicProblems.employees;

public class Java8PartitionBy {

    public static void main(String[] args) {

        // Partition By Salary >= 50000
        System.out.println(employees.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 50000)));
        System.out.println(employees.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 50000, Collectors.averagingDouble(Employee::getSalary))));
        System.out.println(employees.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 50000, Collectors.reducing(0.0, Employee::getSalary, Double::sum))));

    }

}
