package com.mylearnings.java.basicJava;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


//@Value also does the below
@Getter
final class ImmutablePerson {
    private final String name;
    private final int age;
    private final Employee employee;

    public ImmutablePerson(String name, int age, Employee employee) {
        this.name = name;
        this.age = age;
        this.employee = employee;
    }

    public Employee getEmployee() throws CloneNotSupportedException {
        return (Employee) employee.clone(); // Return a copy of the Employee rather than returning actual reference
    }

}

@AllArgsConstructor
@Getter
@Setter
class Employee implements Cloneable {

    private String name;
    private int employeeId;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Employee(this.name, this.employeeId);
    }

}

public class ImmutableClassExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        Employee employee = new Employee("John", 123);
        ImmutablePerson person = new ImmutablePerson("Alice", 30, employee);

        Employee employeeReference = person.getEmployee();
        employeeReference.setName("something");

        String name = person.getName();
        name = "BorderLand";

        // Although I am modifying the reference, It won't be modified.
        System.out.println(person.getName() +" : "+person.getAge() + " : "+person.getEmployee().getName());

    }

}