package com.mylearnings.java.ses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeMgmt {

    public static void main(String[] args) {

        List<EmployeeClass> listOfEmployees = List.of(new EmployeeClass(1, "ab", 123, "CSE"),
                new EmployeeClass(1, "jo", 163, "CSE"),
                new EmployeeClass(1, "pr", 133, "ECE"),
                new EmployeeClass(1, "ve", 113, "CSE"),
                new EmployeeClass(1, "een", 143, "ECE"));

        // highest salary in each department
        Map<String, List<EmployeeClass>> employeesGroupedByDept = listOfEmployees.stream().collect(Collectors.groupingBy(EmployeeClass::getDept));
        Map<String, EmployeeClass> employeesWithHighestSalary = employeesGroupedByDept.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().max(Comparator.comparingLong(EmployeeClass::getSalary)).get()));
        System.out.println(employeesWithHighestSalary);

        // nth highesh salary

        int n = 4;
        EmployeeClass nthHighestSalariedEmployee = listOfEmployees.stream().sorted((e1, e2) -> e2.getSalary() - e1.getSalary())
                .skip(n - 1).limit(1).findFirst().get();
        System.out.println(nthHighestSalariedEmployee);

        HashSet<EmployeeClass> sets = new HashSet<>();


        EmployeeClass employeeClass1 = new EmployeeClass(1, "ab", 123, "CSE");
        EmployeeClass employeeClass2 = new EmployeeClass(1, "ab", 123, "CSE");

        sets.add(employeeClass1);
        sets.add(employeeClass2);

        System.out.println(employeeClass1.equals(employeeClass2));
        System.out.println(sets.size());

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmployeeClass {

    // id, name, salary, dept;
    private Integer id;
    private String name;
    private int salary;
    private String dept;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeClass that)) return false;
        return Objects.equals(id, that.id);
    }

}
