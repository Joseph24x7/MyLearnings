package com.mylearnings.java.advanced_java;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mylearnings.java.advanced_java.Java8BasicProblems.employees;
import static com.mylearnings.java.advanced_java.Java8BasicProblems.transactions;

public class Java8GroupingByReducing {

    void main() {

        // Sum all the Salaries
        System.out.println("Sum Everyone's salary");
        System.out.println(employees.stream().collect(Collectors.reducing(0.0, Employee::getSalary, Double::sum)));
        System.out.println(employees.stream().map(Employee::getSalary).reduce(0.0, Double::sum));
        System.out.println(employees.stream().mapToDouble(Employee::getSalary).sum());
        System.out.println("--------------------------------------");

        // Sum Salaries by Department & Max Salaries by Department
        System.out.println("Group by Department and Sum all the Salary and Find the Max Salary");
        System.out.println(employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.reducing(0.0, Employee::getSalary, Double::sum))));
        Map<String, Optional<Employee>> maxSalaryByDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.reducing((Employee e1, Employee e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2)));
        System.out.println(maxSalaryByDepartment);
        System.out.println("--------------------------------------");

        // You have a list of Transaction records (id, amount, account_holder, type: DEBIT/CREDIT).
        // Write a method using Java Streams to return a Map<String, Double> where the key is the account_holder and
        // value is the net balance (Credits - Debits).
        System.out.println("Group by AccountHolder and Debit/Credit based on type and Sum it");
        System.out.println(transactions.stream().collect(Collectors.groupingBy(Transaction::getAccount_holder, Collectors.reducing(0.0, e -> {
            if (e.getType().equals("CREDIT")) {
                return e.getAmount();
            } else {
                return -1 * e.getAmount();
            }
        }, Double::sum))));
        System.out.println("--------------------------------------");

    }

}
