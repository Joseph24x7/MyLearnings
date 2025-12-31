package com.mylearnings.java.advanced_java;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class Java8BasicProblems {

    public static final List<Employee> employees = new ArrayList<>();
    public static final List<Transaction> transactions = new ArrayList<>();

    static {
        employees.add(new Employee(1, "John", "MECH", 40000D));
        employees.add(new Employee(2, "Jane", "CSE", 50000D));
        employees.add(new Employee(3, "Ben", "ECE", 30000D));
        employees.add(new Employee(4, "Mat", "MECH", 70000D));
        employees.add(new Employee(5, "Jane", "CSE", 50000D));

        transactions.add(new Transaction(1, 1000.0, "A", "CREDIT"));
        transactions.add(new Transaction(2, 200.0, "A", "DEBIT"));
        transactions.add(new Transaction(3, 500.0, "B", "CREDIT"));
        transactions.add(new Transaction(4, 150.0, "A", "DEBIT"));
        transactions.add(new Transaction(5, 100.0, "B", "DEBIT"));
    }

    void main() {

        // Q1 convert all the String to upper case and join them
        List<String> strings = List.of("apple", "banana", "cherry", "date", "apple", "banana");
        System.out.println("------Q1------");
        System.out.println(strings.parallelStream().map(String::toUpperCase).collect(Collectors.joining(",")));
        System.out.println("--------------");

        // Q2 Find the occurrences of each string
        System.out.println("------Q2------");
        System.out.println(strings.parallelStream().collect(Collectors.groupingBy(s -> s, Collectors.counting())));
        System.out.println("--------------");

        // Q3 Given a list of integers, find the maximum and minimum value element present in it using Stream functions?
        System.out.println("------Q3------");
        List<Integer> integers = List.of(5, 3, 8, 1, 2, 7, 4, 6);

        System.out.println("Option 1:");
        System.out.println(integers.stream().min(Comparator.naturalOrder()).get());
        System.out.println(integers.stream().max(Integer::compareTo).get());

        System.out.println("Option 2:");
        List<Integer> sorted = integers.stream().sorted().toList();
        System.out.println(sorted.getFirst());
        System.out.println(sorted.getLast());

        System.out.println("--------------");

        // Q4 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println("------Q4------");
        System.out.println("Option 1:");
        System.out.println(integers.stream().sorted((i1, i2) -> i2 - i1).toList());
        System.out.println("Option 2:");
        System.out.println(integers.stream().sorted(Comparator.reverseOrder()).toList());
        System.out.println("--------------");

        // Q5 - sum - average and count
        System.out.println("------Q5------");
        System.out.println("Sum: " + integers.stream().mapToInt(Integer::intValue).sum());
        System.out.println("Average: " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("--------------");

        // Q6 - Merge two sorted array list
        System.out.println("------Q6------");
        List<List<Integer>> listOfLists = List.of(List.of(1, 3, 5, 7, 9), List.of(2, 4, 6, 8, 10));
        System.out.println(listOfLists.stream().flatMap(Collection::stream).sorted().toList());
        System.out.println("--------------");

        // Q7 - Program to reverse each word in a given string
        System.out.println("------Q7------");
        String sentence = "Hello World from Java Streams";
        String reversedWords = Arrays.stream(sentence.split(" ")).map(word -> new StringBuilder(word).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println(reversedWords);
        System.out.println("--------------");

        // Grouping By Department and Getting Count
        System.out.println("Grouping By Department and Getting Count");
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getDepartment)));
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting())));
        System.out.println("--------------------------------------");

        // Employee with maximum salary
        System.out.println("Employee with Maximum Salary using Max and Reduce");
        System.out.println(employees.stream().max(Comparator.comparingDouble(Employee::getSalary)));
        System.out.println(employees.stream().reduce((Employee e1, Employee e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2));
        System.out.println("--------------------------------------");

        // Q10 - students with distinction
        // Q13 - find non-repetitive words
        // Q14 - top 3 students by marks
        // Q15 - sort map by key and by value
        // Q16 - Group students by marks

    }

}

@Data
@AllArgsConstructor
class Employee {
    private Integer id;
    private String name;
    private String Department;
    private Double Salary;
}

@Data
@AllArgsConstructor
class Transaction {
    private int id;
    private Double amount;
    private String account_holder;
    private String type;
}