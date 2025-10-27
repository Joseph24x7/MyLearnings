package com.mylearnings.java.basicJava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
final class Company {
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
        return new Employee(this.name, this.employeeId);
    }

}

@Value
class CompanyValue {
    String name;
    Employee employee;
}

public record ImmutableClass(
        String name,
        Employee employee
) {}