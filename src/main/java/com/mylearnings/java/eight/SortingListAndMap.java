package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortingListAndMap {

    public static void main(String[] args) {

        List<Employee> students = List.of(new Employee(1, 100), new Employee(2, 80),
                new Employee(3, 85), new Employee(4, 92), new Employee(5, 50));

        List<Employee> sortedStudentsFirstThree = students.stream().sorted((s1, s2) -> s2.getMarks() - s1.getMarks())
                .limit(3).skip(0)
                .toList();

        sortedStudentsFirstThree.forEach(System.out::println);

        System.out.println("----------");

        Map<Employee, Integer> employeeMap = Map.of(
                new Employee(4, 100), 20,
                new Employee(3, 80), 25,
                new Employee(5, 70), 30,
                new Employee(2, 90), 15,
                new Employee(6, 85), 10);

        Map<Employee, Integer> sortedMapUsingMarks = employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((e1, e2) -> e1.getMarks() - e2.getMarks()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Map<Employee, Integer> descSortedMapUsingValue = employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> v2 - v1))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        sortedMapUsingMarks.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("----------");
        descSortedMapUsingValue.forEach((k, v) -> System.out.println(k + " : " + v));

    }

}

@AllArgsConstructor
@Data
class Employee {

    private Integer id;
    private Integer marks;

}
