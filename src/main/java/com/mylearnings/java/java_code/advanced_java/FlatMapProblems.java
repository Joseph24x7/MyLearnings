package com.mylearnings.java.java_code.advanced_java;

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlatMapProblems {

    public static void main(String[] args) {

        List<Project> projects = Arrays.asList(

                new Project(Arrays.asList(EmployeeNew.builder().tasks(Arrays.asList(new Tasks("Design API"), new Tasks("Implement Service"), new Tasks("Code Review"))).build(),

                        EmployeeNew.builder().tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("Fix Bugs"))).build())),

                new Project(Arrays.asList(EmployeeNew.builder().tasks(Arrays.asList(new Tasks("DB Schema Design"), new Tasks("Optimize Queries"))).build(),

                        EmployeeNew.builder().tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup"))).build())),

                new Project(Arrays.asList(EmployeeNew.builder().tasks(Arrays.asList(new Tasks("Kafka Integration"), new Tasks("Event Processing"), new Tasks("Error Handling"))).build(),

                        EmployeeNew.builder().tasks(Arrays.asList(new Tasks("Write Unit Tests"), new Tasks("CI/CD Pipeline Setup"))).build())));

        List<String> taskDescriptions =
                projects.stream()
                        .flatMap(project -> project.getEmployees().stream())
                        .flatMap(employee -> employee.getTasks().stream())
                        .map(Tasks::getDescription)
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(3)
                        .map(k -> k.getKey())
                        .toList();

        System.out.println(taskDescriptions);

    }

}

@Getter
@AllArgsConstructor
class Project {
    private List<EmployeeNew> employees;

    public List<EmployeeNew> getEmployees() {
        return employees;
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class EmployeeNew {
    private List<Tasks> tasks;
}


