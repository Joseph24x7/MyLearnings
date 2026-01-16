package com.mylearnings.java.java_code.advanced_java;

import lombok.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByKey;

public class FlatMapProblems {

    public static void main() {

        FlatMapProblems flatMapProblems = new FlatMapProblems();

        // Flatten a List of List of Tasks and print each task
        flatMapProblems.getListOfListOfTasks().stream().flatMap(Collection::stream).forEach(System.out::print);
        System.out.println("----------");

        // Find the top 3 most assigned tasks across all employees in all projects
        System.out.println("----- Top 3 taskDescriptions-----");
        List<String> taskDescriptions =
                flatMapProblems.getProjects().stream()
                        .flatMap(project -> project.getEmployees().stream())
                        .flatMap(employee -> employee.getTasks().stream())
                        .map(Tasks::getDescription)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .toList();
        System.out.println(taskDescriptions);
        System.out.println("----- Top 3 taskDescriptions-----");


        // Get List of Flattended Employees
        System.out.println("----- Top 3 getListOfEmployees -----");
        List<EmployeeNew> employees = flatMapProblems.getListOfEmployees(flatMapProblems);
        System.out.println(employees);
        System.out.println("----- Top 3 getListOfEmployees -----");

        System.out.println("----- groupByDepartment, value is task desciption -----");
        Map<String, List<String>> groupByDepartment = employees.stream().collect(
                Collectors.groupingBy(EmployeeNew::getDepartment,
                        Collectors.flatMapping(e -> e.getTasks().stream().map(task -> task.getDescription()), Collectors.toList())));

        System.out.println(groupByDepartment);
        System.out.println("----- groupByDepartment, value is task desciption -----");

        // Sorting an HashMap by values in descending order
        System.out.println("-------------Sorting an HashMap by Key--------------------");

        LinkedHashMap<String, List<String>> sortedHashMap = groupByDepartment.entrySet()
                .stream().sorted(Map.Entry.<String, List<String>>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,             // key mapper
                        Map.Entry::getValue, (oldValue, _) -> oldValue, LinkedHashMap::new));

        System.out.println(sortedHashMap);

        System.out.println("-------------Sorting an HashMap by Key--------------------");

    }

    private List<List<Tasks>> getListOfListOfTasks() {
        return Arrays.asList(
                Arrays.asList(new Tasks("Design API"), new Tasks("Implement Service"), new Tasks("Code Review")),
                Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("Fix Bugs")),
                Arrays.asList(new Tasks("DB Schema Design"), new Tasks("Optimize Queries")),
                Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup")),
                Arrays.asList(new Tasks("Kafka Integration"), new Tasks("Event Processing"), new Tasks("Error Handling")),
                Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup"))
        );
    }

    private static List<EmployeeNew> getListOfEmployees(FlatMapProblems flatMapProblems) {

        List<Project> projects = flatMapProblems.getProjects();

        return projects.stream().flatMap(e -> e.getEmployees().stream()).toList();

    }

    private List<Project> getProjects() {
        return Arrays.asList(
                new Project(Arrays.asList(EmployeeNew.builder().department("CSE").tasks(Arrays.asList(new Tasks("Design API"), new Tasks("Implement Service"), new Tasks("Code Review"))).build(),
                        EmployeeNew.builder().department("ECE").tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("Fix Bugs"))).build())),
                new Project(Arrays.asList(EmployeeNew.builder().department("MECH").tasks(Arrays.asList(new Tasks("DB Schema Design"), new Tasks("Optimize Queries"))).build(),
                        EmployeeNew.builder().department("CSE").tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup"))).build())),
                new Project(Arrays.asList(EmployeeNew.builder().department("ECE").tasks(Arrays.asList(new Tasks("Kafka Integration"), new Tasks("Event Processing"), new Tasks("Error Handling"))).build(),
                        EmployeeNew.builder().department("MECH").tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup"))).build()))
        );
    }

}

@Getter
@AllArgsConstructor
class Project {
    private List<EmployeeNew> employees;
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class EmployeeNew {
    private String department;
    private List<Tasks> tasks;
}

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class Tasks {
    private String description;
}


