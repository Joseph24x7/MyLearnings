package com.mylearnings.java.basicJava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

// 1. Using a final class with private final fields and no setters to create an immutable class
final class Company {
    @Getter
    private final String name;

    private final Employee employee;

    public Company(String name, Employee employee) {
        this.name = name;
        this.employee = employee;
    }

    public Employee getEmployee() throws CloneNotSupportedException {
        return (Employee) employee.clone();
    }
}

@AllArgsConstructor
@NoArgsConstructor
class Employee implements Cloneable {

    private String name;
    private int employeeId;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new Employee(this.name, this.employeeId);
    }

}

// 2. Using Lombok's @Value to create an immutable class. But does only shallow copy.
@Value
public class WaysToCreateImmutableClass {
    String name;
    Employee employee; // It creates only a shallow copy of Employee.
}

// 3. Using Record to create an immutable class. But we need to create object using constructor with all parameters.
record CompanyRecord(
        String name,
        Employee employee
) {
}