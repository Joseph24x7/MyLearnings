package com.mylearnings.java.eight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class Java8Problems {

    public static void main(String[] args) {

        List<Integer> myList = Arrays.asList(10, 15, 8, 49, 25, 98, 32);

        //Q1 Given a list of integers, find out all the even numbers exist in the list using Stream functions?
        System.out.println(myList.stream().filter(t -> t % 2 == 0).toList());

        //Q2 Given a list of integers, find out all the numbers starting with 1 using Stream functions
        System.out.println(myList.stream().map(s -> s + "").filter(s -> s.startsWith("1")).toList());

        List<Integer> myList2 = Arrays.asList(10, 15, 8, 49, 25, 98, 98, 32, 15);
        //Q3 How to find duplicate elements in a given integers list in java using Stream functions?
        Set<Integer> set = new HashSet<>();
        System.out.println(myList2.stream().filter(i -> !set.add(i)).toList());

        //Q4 Given the list of integers, find the first element of the list using Stream functions?
        System.out.println(myList2.stream().findFirst().get());

        //Q5 Given a list of integers, find the maximum value element present in it using Stream functions?
        //System.out.println(myList2.stream().max((i, j) -> i - j).get());
        System.out.println(myList2.stream().max(Comparator.comparingInt(i -> i)).get());

        //Q6 Given a list of integers, find the minimum value element present in it using Stream functions?
        //System.out.println(myList2.stream().min((i, j) -> i - j).get());
        System.out.println(myList2.stream().min(Comparator.comparingInt(i -> i)).get());

        String input = "Java Hungry Blog Alive is Awesome";
        //Q7 Given a String, find the first repeated character in it using Stream functions?
        Set<Character> set2 = new HashSet<>();
        System.out.println(input.chars().mapToObj(e -> (char) e).filter(i -> !set2.add(i)).findFirst());

        //Q8 Given a list of integers, sort all the values present in it using Stream functions?
        System.out.println(myList2.stream().sorted(Integer::compare).collect(Collectors.toList()));

        //Q9 Given a list of integers, sort all the values present in it in descending order using Stream functions?
        System.out.println(myList2.stream().sorted((i1, i2) -> i2 - i1).collect(Collectors.toList()));

        //Q10 Java 8 Program to Print ten random numbers using forEach?
        new Random().ints().limit(10).forEach(System.out::print);

        //Q11 - sum - average and count
        System.out.println(myList.stream().mapToInt(i -> i).sum());
        System.out.println(myList.stream().mapToInt(i -> i).average().orElse(0d));
        System.out.println(myList.stream().mapToInt(i -> i).count());

        //Q11 - students with distinction
        List<Student> students = Arrays.asList(new Student("john", 85.0f), new Student("Ajay", 70.0f), new Student("Mani", 85.0f), new Student("Vikram", 60.0f));
        System.out.println(students.stream().filter(s -> s.getMarks() >= 80.0f).peek(s -> s.setDistinction(true)).toList());
    }

}

@Data
@AllArgsConstructor
class Student {

    private String name;
    private Float marks;
    private Boolean distinction;

    public Student(String name, Float marks) {
        super();
        this.name = name;
        this.marks = marks;
    }

}
